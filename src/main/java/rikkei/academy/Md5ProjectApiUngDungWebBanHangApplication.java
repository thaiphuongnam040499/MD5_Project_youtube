package rikkei.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rikkei.academy.service.uploadFile.FileStorageService;

@SpringBootApplication
public class Md5ProjectApiUngDungWebBanHangApplication implements CommandLineRunner {
	@Autowired
	private FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(Md5ProjectApiUngDungWebBanHangApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileStorageService.init();
	}
}
