package com.appteam4.postella.dto;

import java.io.Serializable;
import java.util.Arrays;

public class Image implements Serializable {
	@Override
	public String toString() {
		return "Image{" +
				"us_no=" + us_no +
				", prd_no=" + prd_no +
				", can_no=" + can_no +
				", rev_no=" + rev_no +
				", img_file=" + Arrays.toString(img_file) +
				", img_use='" + img_use + '\'' +
				", img_oname='" + img_oname + '\'' +
				", img_type='" + img_type + '\'' +
				", pg_no=" + pg_no +
				", encodedFile='" + encodedFile + '\'' +
				", nullOrnot=" + nullOrnot +
				'}';
	}

	public int getUs_no() {
		return us_no;
	}

	public void setUs_no(int us_no) {
		this.us_no = us_no;
	}

	public int getPrd_no() {
		return prd_no;
	}

	public void setPrd_no(int prd_no) {
		this.prd_no = prd_no;
	}

	public int getCan_no() {
		return can_no;
	}

	public void setCan_no(int can_no) {
		this.can_no = can_no;
	}

	public int getRev_no() {
		return rev_no;
	}

	public void setRev_no(int rev_no) {
		this.rev_no = rev_no;
	}

	public byte[] getImg_file() {
		return img_file;
	}

	public void setImg_file(byte[] img_file) {
		this.img_file = img_file;
	}

	public String getImg_use() {
		return img_use;
	}

	public void setImg_use(String img_use) {
		this.img_use = img_use;
	}

	public String getImg_oname() {
		return img_oname;
	}

	public void setImg_oname(String img_oname) {
		this.img_oname = img_oname;
	}

	public String getImg_type() {
		return img_type;
	}

	public void setImg_type(String img_type) {
		this.img_type = img_type;
	}

	public int getPg_no() {
		return pg_no;
	}

	public void setPg_no(int pg_no) {
		this.pg_no = pg_no;
	}

	public String getEncodedFile() {
		return encodedFile;
	}

	public void setEncodedFile(String encodedFile) {
		this.encodedFile = encodedFile;
	}

	public boolean isNullOrnot() {
		return nullOrnot;
	}

	public void setNullOrnot(boolean nullOrnot) {
		this.nullOrnot = nullOrnot;
	}

	/*private int img_no;*/ 			// 이미지 파일 식별 번호 NOT NULL
	private int us_no; 					// 회원 고유 번호
	private int prd_no; 				// 상품 고유 번호
	private int can_no; 				// 교환, 반품 취소 번호
	private int rev_no; 				// 리뷰 식별 번호
	
	private byte[] img_file; 			// 이미지 파일 NOT NULL
	private String img_use; 			// 이미지 사용 용도
	private String img_oname; 			// 이미지 원본 파일명  NOT NULL
	private String img_type; 			// 이미지 MIME 타입  NOT NULL
	private int pg_no; 					// 상품 대분류
	private String encodedFile; 		// 인코딩한 파일 저장
	private boolean nullOrnot;			// null 여부 확인
}
