package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.*;
import com.example.clinic_model.model.Doctor;
import com.example.clinic_model.model.Image;
import com.example.clinic_model.model.Patient;
import com.example.clinic_model.model.Report;
import com.example.clinic_model.repository.DoctorRepository;
import com.example.clinic_model.repository.ImageRepository;
import com.example.clinic_model.repository.PatientRepository;
import com.example.clinic_model.repository.ReportRepository;
import com.example.clinic_model.service.DoctorService;
import com.example.clinic_model.service.FileService;
import com.example.clinic_model.service.PatientService;
import com.example.clinic_model.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {
    @Value("${file.upload.path}")
    private String uploadPath;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    //upload file test
    @Override
    public ImageDTO uploadFile(ImageDTO imageDTO) {
        MultipartFile file=imageDTO.getImage();
        if(file.isEmpty()) throw new RuntimeException("File not found");
        if(!this.isFileValid(file)) throw new RuntimeException("Unsupported Format");
        String fileName =this.generateFileName(file);
        Image savedImage;
        try{
            Files.copy(file.getInputStream(),this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
            savedImage=this.imageRepository.save(new Image(fileName));
        }
        catch (IOException exception){
            throw new RuntimeException("File upload Error");
        }
        return ImageDTO.builder().imageId(savedImage.getImageId()).build();
    }


    //test method->to upload patient profile pic
    @Override
    public ImageDTO uploadPatientProfilePic(Long patientID,ImageDTO imageDTO) {
        //getting patient
        PatientDTO patientDTO=this.patientService.getPatientById(patientID);
        Patient patient=modelMapper.map(patientDTO,Patient.class);

        MultipartFile file=imageDTO.getImage();
        if(file.isEmpty()) throw new RuntimeException("File not found");
        if(!this.isFileValid(file)) throw new RuntimeException("Unsupported Format");
        String fileName =this.generateFileName(file);
        Image savedImage;

        try{
            System.out.println("hello there");
            Files.copy(file.getInputStream(),this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("bye there");
            savedImage=this.imageRepository.save(new Image(fileName));
            System.out.println(savedImage);
            savedImage.setPatient(patient);
            System.out.println(savedImage);
        }
        catch (IOException exception){
            throw new RuntimeException("File upload Error");
        }
        return ImageDTO.builder().imageId(savedImage.getImageId()).build();
    }

    //get patient profile pic
    @Override
    public ImageDownloadDTO getPatientProfilePic(Long patientId) {
        Image image=this.imageRepository.findByPatientPatientId(patientId)
                .orElseThrow(()->new RuntimeException("Image not found"));
        try{
            MediaType mediaType=this.getMediaType(image.getFileName());
            Resource resource=new UrlResource(this.generatedFilePath(image.getFileName()).toUri());
            return new ImageDownloadDTO(resource,mediaType);
        }
        catch (IOException exception){
            throw new RuntimeException("File read error");
        }
    }

    //upload doctor profile pic
    @Override
    public ImageDTO uploadDoctorProfilePic(Long doctorId, ImageDTO imageDTO) {
        DoctorDTO doctorDTO=this.doctorService.getDoctorById(doctorId);
        Doctor doctor=modelMapper.map(doctorDTO,Doctor.class);

        MultipartFile file=imageDTO.getImage();
        if(file.isEmpty()) throw new RuntimeException("File not found");
        if(!this.isFileValid(file)) throw new RuntimeException("Unsupported Format");
        String fileName =this.generateFileName(file);
        Image savedImage;

        try{
            System.out.println("hello there");
            Files.copy(file.getInputStream(),this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("bye there");
            savedImage=this.imageRepository.save(new Image(fileName));

            savedImage.setDoctor(doctor);

        }
        catch (IOException exception){
            throw new RuntimeException("File upload Error");
        }
        return ImageDTO.builder().imageId(savedImage.getImageId()).build();
    }

    //get doctor profile pic
    @Override
    public ImageDownloadDTO getDoctorProfilePic(Long doctorId) {
        Image image=this.imageRepository.findByDoctorDoctorId(doctorId)
                .orElseThrow(()->new RuntimeException("Image not found"));
        try{
            MediaType mediaType=this.getMediaType(image.getFileName());
            Resource resource=new UrlResource(this.generatedFilePath(image.getFileName()).toUri());
            return new ImageDownloadDTO(resource,mediaType);
        }
        catch (IOException exception){
            throw new RuntimeException("File read error");
        }
    }

    //upload report pic
    @Override
    public ImageDTO uploadReportPic(Long reportId, ImageDTO imageDTO) {
        ReportDTO reportDTO=this.reportService.getReportById(reportId);
        Report report =modelMapper.map(reportDTO,Report.class);


        MultipartFile file=imageDTO.getImage();
        if(file.isEmpty()) throw new RuntimeException("File not found");
        if(!this.isFileValid(file)) throw new RuntimeException("Unsupported Format");
        String fileName =this.generateFileName(file);
        Image savedImage;

        try{
            System.out.println("hello there");
            Files.copy(file.getInputStream(),this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("bye there");
            savedImage=this.imageRepository.save(new Image(fileName));
            savedImage.setReport(report);

        }
        catch (IOException exception){
            throw new RuntimeException("File upload Error");
        }
        return ImageDTO.builder().imageId(savedImage.getImageId()).build();
    }

    //to get report pic
    @Override
    public ImageDownloadDTO getReportPic(Long patientId) {
        Report report=reportRepository.findByPatientPatientId(patientId)
                .orElseThrow(()->new RuntimeException("Image not found"));

        Image image=this.imageRepository.findByReportReportId(report.getReportId())
                .orElseThrow(()->new RuntimeException("Image not found"));
        try{
            MediaType mediaType=this.getMediaType(image.getFileName());
            Resource resource=new UrlResource(this.generatedFilePath(image.getFileName()).toUri());
            return new ImageDownloadDTO(resource,mediaType);
        }
        catch (IOException exception){
            throw new RuntimeException("File read error");
        }
    }

    @Override
    public ImageDownloadDTO downloadFile(Long imageId) {
        Image image=this.imageRepository.findById(imageId)
                .orElseThrow(()->new RuntimeException("Image not found"));
        try{
            MediaType mediaType=this.getMediaType(image.getFileName());
            Resource resource=new UrlResource(this.generatedFilePath(image.getFileName()).toUri());
            return new ImageDownloadDTO(resource,mediaType);
        }
        catch (IOException exception){
            throw new RuntimeException("File read error");
        }
    }
    //to get patient profile pic


    private MediaType getMediaType(String fileName){
        int index=fileName.lastIndexOf('.');
        String extension=fileName.substring(index+1);

        if(extension.equals("png")){
            return MediaType.IMAGE_PNG;
        } else if (extension.equals("jpeg")||extension.equals("jpg") ) {
            return MediaType.IMAGE_JPEG;
        }
        throw new RuntimeException("Invalid Media Type");
    }

    private Boolean isFileValid(MultipartFile file){
        return Objects.equals(file.getContentType(),"image/png")||
                Objects.equals(file.getContentType(),"image/jpeg")||
                Objects.equals(file.getContentType(),"image/jpg");
    }
    private Path generatedFilePath(String fileName){
        return Paths.get(uploadPath+ File.separator+fileName).toAbsolutePath();
    }
    private String generateFileName(MultipartFile file){
        return RandomStringUtils.randomAlphanumeric(10)+ "_" +file.getOriginalFilename();
    }
}
