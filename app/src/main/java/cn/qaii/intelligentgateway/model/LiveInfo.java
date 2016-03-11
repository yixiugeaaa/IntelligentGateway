package cn.qaii.intelligentgateway.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "LiveInfo")
public class LiveInfo {

	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private int analystId;
	@DatabaseField
	private String analystNickName;
	@DatabaseField
	private String analystHeadImgUrl;
	@DatabaseField
	private String title;
	@DatabaseField
	private Date startTime=new Date();
	@DatabaseField
	private Date endTime;
	@DatabaseField
	private boolean closed;
	@DatabaseField
	private boolean opened;
	@DatabaseField
	private boolean canceled;
	@DatabaseField
	private String domain;
	@DatabaseField
	private int soundRoomId;
	@DatabaseField
	private int videoRoomId;
	@DatabaseField
	private String password;
	@DatabaseField
	private boolean living;
	@DatabaseField
	private int liveStatus;
	@DatabaseField
	private int status;
	@DatabaseField
	private String description;
	@DatabaseField
	private int listenerCount;
	@DatabaseField
	private int praiseCount;
	@DatabaseField
	private int shareCount;
	@DatabaseField
	private String noticeContent;
	@DatabaseField
	private int privateId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnalystId() {
		return analystId;
	}
	public void setAnalystId(int analystId) {
		this.analystId = analystId;
	}
	public String getAnalystNickName() {
		return analystNickName;
	}
	public void setAnalystNickName(String analystNickName) {
		this.analystNickName = analystNickName;
	}
	public String getAnalystHeadImgUrl() {
		return analystHeadImgUrl;
	}
	public void setAnalystHeadImgUrl(String analystHeadImgUrl) {
		this.analystHeadImgUrl = analystHeadImgUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getSoundRoomId() {
		return soundRoomId;
	}
	public void setSoundRoomId(int soundRoomId) {
		this.soundRoomId = soundRoomId;
	}
	public int getVideoRoomId() {
		return videoRoomId;
	}
	public void setVideoRoomId(int videoRoomId) {
		this.videoRoomId = videoRoomId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLiving() {
		return living;
	}
	public void setLiving(boolean living) {
		this.living = living;
	}
	public int getLiveStatus() {
		return liveStatus;
	}
	public void setLiveStatus(int liveStatus) {
		this.liveStatus = liveStatus;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getListenerCount() {
		return listenerCount;
	}
	public void setListenerCount(int listenerCount) {
		this.listenerCount = listenerCount;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public int getPrivateId() {
		return privateId;
	}
	public void setPrivateId(int privateId) {
		this.privateId = privateId;
	}
}
