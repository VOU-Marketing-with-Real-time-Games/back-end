package com.hcmus.voucherservice.controller;

import com.hcmus.voucherservice.dto.UpdateVoucherDto;
import com.hcmus.voucherservice.dto.VoucherDto;
import com.hcmus.voucherservice.dto.VoucherResponseDto;
import com.hcmus.voucherservice.dto.VoucherUserRequestDto;
import com.hcmus.voucherservice.exception.ExistedVoucherException;
import com.hcmus.voucherservice.exception.VoucherNotFoundException;
import com.hcmus.voucherservice.service.VoucherCampaignService;
import com.hcmus.voucherservice.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v3/vouchers")
public class VoucherController {
    private final VoucherService voucherService;
    private final VoucherCampaignService voucherCampaignService;
    /**
     * Create a new voucher.
     *
     * @param voucherDTO the VoucherDto containing the details of the voucher
     * @return the created VoucherResponseDto
     * @throws ExistedVoucherException if the voucher already exists
     */
    @PostMapping
    public ResponseEntity<?> createVoucher(@Valid @RequestBody VoucherDto voucherDTO) throws ExistedVoucherException {
        VoucherResponseDto voucher = voucherService.createVoucher(voucherDTO);
        return new ResponseEntity<>(voucher, HttpStatus.CREATED);
    }
    /**
     * Get all vouchers.
     *
     * @param pageable the pageable information
     * @return a list of VoucherResponseDto
     */
    @GetMapping
    public ResponseEntity<?> getAllVouchers(Pageable pageable) {
        Page<VoucherResponseDto> voucherList = voucherService.getAllVouchers(pageable);
        return new ResponseEntity<>(voucherList,HttpStatus.OK);
    }
    /**
     * Get a voucher by its ID.
     *
     * @param id the ID of the voucher
     * @return the VoucherResponseDto
     * @throws VoucherNotFoundException if the voucher is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAVoucher(@PathVariable String id) throws VoucherNotFoundException {
        VoucherResponseDto voucher = voucherService.getVoucher(id);
        return new ResponseEntity<>(voucher,HttpStatus.OK);
    }




    /**
     * Update a voucher by its ID.
     *
     * @param id the ID of the voucher
     * @param voucherDto the UpdateVoucherDto containing the details of the voucher
     * @return the updated VoucherResponseDto
     * @throws VoucherNotFoundException if the voucher is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?>  updateAVoucher(@PathVariable String id,@Valid @RequestBody UpdateVoucherDto voucherDto) throws VoucherNotFoundException {
        VoucherResponseDto voucher = voucherService.updateVoucher(id,voucherDto);
        return new ResponseEntity<>(voucher,HttpStatus.OK);
    }
    /**
     * Delete a voucher by its ID.
     *
     * @param id the ID of the voucher
     * @return a ResponseEntity with no content
     * @throws VoucherNotFoundException if the voucher is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable String id) throws VoucherNotFoundException {
        voucherService.deleteVoucherById(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Get all vouchers of a user by its ID.
     *
     * @param userId the ID of the user
     * @return a list of VoucherResponseDto
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserVouchers(@PathVariable Long userId) {
        return new ResponseEntity<>(voucherService.getVouchersByUserId(userId),HttpStatus.OK);
    }
    /**
     * Get all vouchers of a campaign by its ID.
     *
     * @param campaignId the ID of the campaign
     * @return a list of VoucherResponseDto
     */
    @GetMapping("/campaign/{campaignId}")
    public  ResponseEntity<?> getCampaignVouchers(@PathVariable Long campaignId) {
        return new ResponseEntity<>(voucherService.getVouchersByCampaignId(campaignId),HttpStatus.OK);
    }




    @PostMapping("/take-voucher")
    public boolean takeVoucherToUser(@RequestParam Long campaignId, @RequestParam Long userId) {
        return voucherCampaignService.takeVoucherToUser(campaignId, userId);
    }

    @PostMapping("/take-voucher-after-quizz")
    public ResponseEntity<Void> takeVoucherAfterQuizz(@RequestBody VoucherUserRequestDto voucherUserRequestDto) {
        voucherCampaignService.takeVoucherAfterQuiz(voucherUserRequestDto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/distinct-voucher-ids")
    public ResponseEntity<List<String>> getDistinctVoucherIdsByCampaignIds(@RequestBody List<Long> campaignIds) {
        List<String> voucherIds = voucherCampaignService.getDistinctVoucherIdsByCampaignIds(campaignIds);
        return new ResponseEntity<>(voucherIds, HttpStatus.OK);
    }


    @GetMapping("/campaign/{campaignId}/vouchers-given")
    public ResponseEntity<Integer> getNumberOfVouchersGivenToUserByCampaignId(@PathVariable Long campaignId) {
        int numberOfVouchersGiven = voucherCampaignService.getNumberOfVouchersGivenToUserByCampaignId(campaignId);
        return ResponseEntity.ok(numberOfVouchersGiven);
    }
}
