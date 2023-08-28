package com.example.clinic_model.service;

import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.ImageDownloadDTO;

public interface FileService {
    ImageDTO uploadFile(ImageDTO imageDTO);

    ImageDownloadDTO downloadFile(Long imageId);

    //Upload Patient Profile Pic
    ImageDTO uploadPatientProfilePic(Long patientId,ImageDTO imageDTO);

    //Get doctor profile pic
    ImageDownloadDTO getPatientProfilePic(Long patientId);

    //Upload Doctor Profile Pic
    ImageDTO uploadDoctorProfilePic(Long doctorId,ImageDTO imageDTO);

    //Get doctor profile pic
    ImageDownloadDTO getDoctorProfilePic(Long doctorId);

    //upload image of report
    ImageDTO uploadReportPic(Long patientId,ImageDTO imageDTO);

    //Get Report pic
    ImageDownloadDTO getReportPic(Long doctorId);
    ImageDownloadDTO getReportPic(Long patientId);
    //For News Image
    ImageDownloadDTO getNewsImage(Long newsId);

    //Upload News Image
    ImageDTO uploadNewsImage(Long newsID,ImageDTO imageDTO);
}
