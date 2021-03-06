package camping.model;

public class spot {
	private int sp_no; /* 자리번호 */
	private int camp_no; /* 장소번호 */
	private String type; /* 종류 */
	private int standard_num;/* 기준인원 */
	private int max_num; /* 최대인원 */
	private int price; /* 가격 */
	private String memo; /* 주요사항 */
	private String image;
	private String imagedetail;
	private int hightprice;
	private String sp_name;
	private int reservecheck;
	private double sp_staravg;
	public double getSp_staravg() {
		return sp_staravg;
	}
	public void setSp_staravg(double sp_staravg) {
		this.sp_staravg = sp_staravg;
	}

	
	public int getReservecheck() {
		return reservecheck;
	}
	public void setReservecheck(int reservecheck) {
		this.reservecheck = reservecheck;
	}
	public int getSp_no() {
		return sp_no;
	}
	public void setSp_no(int sp_no) {
		this.sp_no = sp_no;
	}
	public int getCamp_no() {
		return camp_no;
	}
	public void setCamp_no(int camp_no) {
		this.camp_no = camp_no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStandard_num() {
		return standard_num;
	}
	public void setStandard_num(int standard_num) {
		this.standard_num = standard_num;
	}
	public int getMax_num() {
		return max_num;
	}
	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImagedetail() {
		return imagedetail;
	}
	public void setImagedetail(String imagedetail) {
		this.imagedetail = imagedetail;
	}

	public String getSp_name() {
		return sp_name;
	}
	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}
	public int getHightprice() {
		return hightprice;
	}
	public void setHightprice(int hightprice) {
		this.hightprice = hightprice;
	}
}
