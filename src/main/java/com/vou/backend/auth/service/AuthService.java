package com.vou.backend.auth.service;
import com.vou.backend.auth.dto.ExchangeTokenRequest;
import com.vou.backend.auth.dto.OutboundUserResponse;
import com.vou.backend.auth.httpclient.OutboundIdentityClient;
import com.vou.backend.auth.httpclient.OutboundUserClient;
import com.vou.backend.user.dto.UserRequestDto;
import com.vou.backend.user.exception.UserEmailExistedException;
import com.vou.backend.user.exception.UserNameExistedException;
import com.vou.backend.user.exception.UserNotFoundException;
import com.vou.backend.user.model.User;
import com.vou.backend.user.repository.UserRepository;
import com.vou.backend.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private  final OutboundIdentityClient outboundIdentityClient;
    private final OutboundUserClient outboundUserClient;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final OtpService otpService;
    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected  String CLIENT_ID;
    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;
    @NonFinal
    protected String REDIRECT_URI = "http://localhost:3000/authenticate";
    @NonFinal
    protected String GRANT_TYPE = "authorization_code";
    public String login(String userName, String password) throws Exception {
        User user = userRepository.findByUsername(userName);
        if(user==null)
        {
            throw new UserNotFoundException("Invalid username!");
        }
        if(!passwordEncoder.matches(password,user.getPassword()))
        {
            throw new BadCredentialsException("Invalid password!");
        }
        if(!user.getStatus().equals("ACTIVE"))
        {
            throw new BadCredentialsException("User is not active!");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generateToken(user);
    }
    public String outboundAuthentication(String code) throws Exception {
        var response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .build());
        OutboundUserResponse userResponse = outboundUserClient.getUserInfo("json",response.getAccessToken());
        if(userRepository.findByEmail(userResponse.getEmail())!=null)
        {
            return jwtTokenUtil.generateToken(userRepository.findByEmail(userResponse.getEmail()));
        }
        UserRequestDto userDto = UserRequestDto.builder()
                .email(userResponse.getEmail())
                .password("123456")
                .userName(userResponse.getName())
                .fullName(userResponse.getGivenName())
                .avatar(userResponse.getPicture())
                .phoneNumber("123456789")
                .gender(userResponse.getGender())
                .dob(null)
                .role("USER")
                .build();
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),"123456");
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generateToken(user);
    }
    public void sendOTP(String email) throws Exception
    {
        if (userRepository.findByEmail(email) == null) {
            throw new UserNotFoundException("Email does not exist in the system!");
        }
        String otp = emailService.generateOtp();
        otpService.storeOtp(email,otp);
        emailService.sendOtpEmail(email, otp);
    }
    public boolean verifyOTP(String otp,String email) throws Exception
    {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Email does not exist in the system!");
        }
        boolean valid = otpService.verifyOtp(otp,email);
        if(valid)
        {
            user.setStatus("ACTIVE");
            userRepository.save(user);
        }
        return valid;
    }
}
