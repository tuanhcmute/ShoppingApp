package services;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
	public String storeFile(MultipartFile file) throws Exception;
	public byte[] readFileContent(String fileName);
	
}
