package services.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import services.IStorageService;

@Service
public class ImageStorageServiceImpl implements IStorageService{

	@Override
	public String storeFile(MultipartFile file) throws Exception {
		try {
			if(file.isEmpty()) {
				throw new Exception("Fail to store empty file");
			}
			if(!isImageFile(file)) {
				throw new Exception("You can only upload image file");
			}
			
			float fileSizeInMegabytes = (float) (file.getSize() / 1000000.0);
			if(fileSizeInMegabytes > 5.0f) {
				throw new Exception("File must be <= 5MB");
			}
			String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
			String generatedFileName = UUID.randomUUID().toString().replace("-", "");
			generatedFileName = generatedFileName + "." + fileExtension;
			return generatedFileName;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public byte[] readFileContent(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isImageFile(MultipartFile file) {
		String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
		return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"})
				.contains(fileExtension.trim().toLowerCase());
	}
}
