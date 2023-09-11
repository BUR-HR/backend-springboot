package com.bubblebubble.hr.Service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bubblebubble.hr.employee.repository.EmpcardRepository;
import com.bubblebubble.hr.login.dto.EmployeeDTO;
import com.bubblebubble.hr.login.member.entity.Employee;
import com.bubblebubble.hr.util.FileUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

    private final ModelMapper modelMapper;
    private final EmpcardRepository empcardRepository;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    public FileService(ModelMapper modelMapper, EmpcardRepository empcardRepository) {
        this.modelMapper = modelMapper;
        this.empcardRepository = empcardRepository;
    }

    @Transactional
    public String insertFile(EmployeeDTO employeeDTO, MultipartFile fileImgs) {
        log.info("[FileService] insertfile Start ================ ");
        log.info("[FileService] employeeDTO : " + employeeDTO);

        String replaceFileName = null;
        int result = -1; // 결과에 따른 값을 구분하기 위한 용도의 변수

        try {
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, UUID.randomUUID().toString().replace("-", ""), fileImgs);
            System.out.println("Saved file name: " + replaceFileName);

            if (replaceFileName != null) { // 파일 저장이 성공한 경우에만 실행
                employeeDTO.setOriginFile(replaceFileName); // ORIGINFILE에 해당하는 필드 설정
                employeeDTO.setRenameFile(UUID.randomUUID().toString().replace("-", ""));

                log.info("[FileService] insert Image Name : " + replaceFileName);

                Employee insertFile = modelMapper.map(employeeDTO, Employee.class);

                empcardRepository.save(insertFile);
                
                log.info("update check");
                result = 1;
            }

        } catch (Exception e) {
            System.out.println("check");
            if (replaceFileName != null) { // 파일 저장 중에 예외가 발생한 경우에만 삭제 시도
                FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            }
            throw new RuntimeException("파일 저장 중 오류 발생(insertfile): " + e.getMessage(), e);
        }

        log.info("[FileService] insertFile End ================ ");
        return (result > 0) ? "File uploaded successfully" : "File uploaded fail";
    }
}