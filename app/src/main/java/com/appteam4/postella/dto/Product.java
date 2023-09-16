package com.appteam4.postella.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
	@Override
	public String toString() {
		return "Product{" +
				"prd_no=" + prd_no +
				", sel_no=" + sel_no +
				", prd_category='" + prd_category + '\'' +
				", prd_name='" + prd_name + '\'' +
				", prd_org_price=" + prd_org_price +
				", prd_price=" + prd_price +
				", prd_content='" + prd_content + '\'' +
				", prd_stock=" + prd_stock +
				", prd_ship_fee=" + prd_ship_fee +
				", prd_date=" + prd_date +
				", prd_hitcount=" + prd_hitcount +
				", prd_sale_amount=" + prd_sale_amount +
				", prd_yn='" + prd_yn + '\'' +
				", prd_star_avg=" + prd_star_avg +
				", pg_no=" + pg_no +
				", pg_name='" + pg_name + '\'' +
				", pg_imgFile=" + Arrays.toString(pg_imgFile) +
				", img_type='" + img_type + '\'' +
				", encodedFile='" + encodedFile + '\'' +
				", kind='" + kind + '\'' +
				", brand=" + brand +
				", status=" + status +
				", message=" + message +
				", keyword='" + keyword + '\'' +
				", quantity=" + quantity +
				'}';
	}

	public int getPrd_no() {
		return prd_no;
	}

	public void setPrd_no(int prd_no) {
		this.prd_no = prd_no;
	}

	public int getSel_no() {
		return sel_no;
	}

	public void setSel_no(int sel_no) {
		this.sel_no = sel_no;
	}

	public String getPrd_category() {
		return prd_category;
	}

	public void setPrd_category(String prd_category) {
		this.prd_category = prd_category;
	}

	public String getPrd_name() {
		return prd_name;
	}

	public void setPrd_name(String prd_name) {
		this.prd_name = prd_name;
	}

	public int getPrd_org_price() {
		return prd_org_price;
	}

	public void setPrd_org_price(int prd_org_price) {
		this.prd_org_price = prd_org_price;
	}

	public int getPrd_price() {
		return prd_price;
	}

	public void setPrd_price(int prd_price) {
		this.prd_price = prd_price;
	}

	public String getPrd_content() {
		return prd_content;
	}

	public void setPrd_content(String prd_content) {
		this.prd_content = prd_content;
	}

	public int getPrd_stock() {
		return prd_stock;
	}

	public void setPrd_stock(int prd_stock) {
		this.prd_stock = prd_stock;
	}

	public int getPrd_ship_fee() {
		return prd_ship_fee;
	}

	public void setPrd_ship_fee(int prd_ship_fee) {
		this.prd_ship_fee = prd_ship_fee;
	}

	public long getPrd_date() {
		return prd_date;
	}

	public void setPrd_date(long prd_date) {
		this.prd_date = prd_date;
	}

	public int getPrd_hitcount() {
		return prd_hitcount;
	}

	public void setPrd_hitcount(int prd_hitcount) {
		this.prd_hitcount = prd_hitcount;
	}

	public int getPrd_sale_amount() {
		return prd_sale_amount;
	}

	public void setPrd_sale_amount(int prd_sale_amount) {
		this.prd_sale_amount = prd_sale_amount;
	}

	public String getPrd_yn() {
		return prd_yn;
	}

	public void setPrd_yn(String prd_yn) {
		this.prd_yn = prd_yn;
	}

	public int getPrd_star_avg() {
		return prd_star_avg;
	}

	public void setPrd_star_avg(int prd_star_avg) {
		this.prd_star_avg = prd_star_avg;
	}

	public int getPg_no() {
		return pg_no;
	}

	public void setPg_no(int pg_no) {
		this.pg_no = pg_no;
	}

	public String getPg_name() {
		return pg_name;
	}

	public void setPg_name(String pg_name) {
		this.pg_name = pg_name;
	}

	public byte[] getPg_imgFile() {
		return pg_imgFile;
	}

	public void setPg_imgFile(byte[] pg_imgFile) {
		this.pg_imgFile = pg_imgFile;
	}

	public String getImg_type() {
		return img_type;
	}

	public void setImg_type(String img_type) {
		this.img_type = img_type;
	}

	public String getEncodedFile() {
		return encodedFile;
	}

	public void setEncodedFile(String encodedFile) {
		this.encodedFile = encodedFile;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public List<String> getBrand() {
		return brand;
	}

	public void setBrand(List<String> brand) {
		this.brand = brand;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// 개별 상품 테이블 컬럼
	private int prd_no; 				// 상품구분번호
	private int sel_no; 				// 판매자 식별번호
	private String  prd_category; 		// 카테고리 타입, 코드테이블 사용
	private String prd_name; 			// 상품이름
	private int prd_org_price; 			// 정상가
	private int prd_price; 				// 판매가
	private String prd_content; 		// 상품 상세 페이지 내용(텍스트)
	private int prd_stock; 				// 재고
	private int prd_ship_fee; 			// 배송비
	private long prd_date; 				// 상품등록일
	private int prd_hitcount; 			// 조회수
	private int prd_sale_amount; 		// 판매량
	private String prd_yn; 				// 판매여부
	private int prd_star_avg; 			// 상품 당 평균 별점, 범위  0~5
	
	// 상품 대분류
	private int pg_no; 					// 상품 대분류 식별번호
	private String pg_name;				// 상품 대분류 이름
	private byte[] pg_imgFile;          //상품목록 대표 썸네일 이미지파일
	
	// 상품 이미지
	private String img_type;			// img 타입
	private String encodedFile;			// encoded된 이미지파일
	
	// 상품 정렬 및 필터
	private String kind;				// 정렬기준 1:낮은가격순, 2:높은가격순, 3:최신순
	private List<String> brand;			// 브랜드별 필터
	private List<String> status;		// 상품상태별 필터
	private List<String> message;		// 메세지별 필터
	
	// 상품 리스트 검색(헤더)
	private String keyword;				// 검색어
	
	// 상품 수량
	private int quantity;				// 상품 수량
}
