package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.*;
import com.example.clinic_model.model.*;
import com.example.clinic_model.repository.DoctorRepository;
import com.example.clinic_model.repository.ImageRepository;
import com.example.clinic_model.repository.PatientRepository;
import com.example.clinic_model.repository.ReportRepository;
import com.example.clinic_model.service.*;
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
    private NewsService newsService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    //test method->to upload patient profile pic
    @Override
    public ImageDTO uploadPatientProfilePic(Long patientID, ImageDTO imageDTO) {
        PatientDTO patientDTO = this.patientService.getPatientById(patientID);
        Patient patient = modelMapper.map(patientDTO, Patient.class);

        MultipartFile file = imageDTO.getImage();
        if (file.isEmpty()) throw new RuntimeException("File not found");
        if (!this.isFileValid(file)) throw new RuntimeException("Unsupported Format");
        String fileName = this.generateFileName(file);
        Image savedImage;

        try {
            Files.copy(file.getInputStream(), this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
            boolean exists = this.imageRepository.existsByPatientPatientId(patientID);
            if (exists) {
                Optional<Image> existingImage = this.imageRepository.findByPatientPatientId(patientID);
                if (existingImage.isPresent()) {
                    Image image = existingImage.get();
                    // Update the image with the new file name
                    image.setFileName(fileName);
                    savedImage = this.imageRepository.save(image);
                } else {
                    throw new RuntimeException("Existing image not found");
                }
            } else {
                savedImage = this.imageRepository.save(new Image(fileName));
                savedImage.setPatient(patient);
            }
        } catch (IOException exception) {
            throw new RuntimeException("File upload Error");
        }
        return ImageDTO.builder().imageId(savedImage.getImageId()).build();
    }
    //    Upload News Image
    @Override
    public ImageDTO uploadNewsImage(Long newsID, ImageDTO imageDTO) {
        //getting patient
        NewsDTO newsDTO = this.newsService.getNewsById(newsID);
        News news = modelMapper.map(newsDTO, News.class);
        MultipartFile file = imageDTO.getImage();
        if (file.isEmpty()) throw new RuntimeException("File not found");
        if (!this.isFileValid(file)) throw new RuntimeException("Unsupported Format");
        String fileName = this.generateFileName(file);
        Image savedImage;

        try {
            Files.copy(file.getInputStream(), this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
            boolean exists = this.imageRepository.existsByNewsNewsId(newsID);
            if (exists) {
                Optional<Image> existingImage = this.imageRepository.findByNewsNewsId(newsID);
                if (existingImage.isPresent()) {
                    Image image = existingImage.get();
                    // Update the image with the new file name
                    image.setFileName(fileName);
                    savedImage = this.imageRepository.save(image);
                } else {
                    throw new RuntimeException("Existing image not found");
                }
            } else {
                savedImage = this.imageRepository.save(new Image(fileName));
                savedImage.setNews(news);
            }
        } catch (IOException exception) {
            throw new RuntimeException("File upload Error");
        }
        return ImageDTO.builder().imageId(savedImage.getImageId()).build();
    }

    private void deleteImage(Long imageId) {
        // Delete image logic here
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

    //    Get News Image
    public ImageDownloadDTO getNewsImage(Long newsId) {
        Image image=this.imageRepository.findByNewsNewsId(newsId)
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
        DoctorDTO doctorDTO = this.doctorService.getDoctorById(doctorId);
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        MultipartFile file = imageDTO.getImage();
        if (file.isEmpty()) throw new RuntimeException("File not found");
        if (!this.isFileValid(file)) throw new RuntimeException("Unsupported Format");
        String fileName = this.generateFileName(file);
        Image savedImage;

        try {
            Files.copy(file.getInputStream(), this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
            boolean exists = this.imageRepository.existsByDoctorDoctorId(doctorId);
            if (exists) {
                Optional<Image> existingImage = this.imageRepository.findByDoctorDoctorId(doctorId);
                if (existingImage.isPresent()) {
                    Image image = existingImage.get();
                    // Update the image with the new file name
                    image.setFileName(fileName);
                    savedImage = this.imageRepository.save(image);
                } else {
                    throw new RuntimeException("Existing image not found");
                }
            } else {
                savedImage = this.imageRepository.save(new Image(fileName));
                savedImage.setDoctor(doctor);
            }
        } catch (IOException exception) {
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
            Files.copy(file.getInputStream(),this.generatedFilePath(fileName), StandardCopyOption.REPLACE_EXISTING);
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
    public ImageDownloadDTO getReportPic(Long appointmentId) {
        Report report=reportRepository.findByAppointmentAppointmentId(appointmentId)
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
    private MediaType getMediaType(String fileName){
        int index=fileName.lastIndexOf('.');
        String extension=fileName.substring(index+1);

        switch (extension) {
            case "png" -> {
                return MediaType.IMAGE_PNG;
            }
            case "jpeg", "jpg" -> {
                return MediaType.IMAGE_JPEG;
            }
            case "pdf" -> {
                return MediaType.APPLICATION_PDF;
            }
        }
        throw new RuntimeException("Invalid Media Type");
    }

    private Boolean isFileValid(MultipartFile file){
        return Objects.equals(file.getContentType(),"image/png")||
                Objects.equals(file.getContentType(),"image/jpeg")||
                Objects.equals(file.getContentType(),"image/jpg")||
                Objects.equals(file.getContentType(),"application/pdf");
    }
    private Path generatedFilePath(String fileName){
        return Paths.get(uploadPath+ File.separator+fileName).toAbsolutePath();
    }
    private String generateFileName(MultipartFile file){
        return RandomStringUtils.randomAlphanumeric(10)+ "_" +file.getOriginalFilename();
    }
}
