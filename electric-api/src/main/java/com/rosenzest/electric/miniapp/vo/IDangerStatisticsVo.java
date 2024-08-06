package com.rosenzest.electric.miniapp.vo;

import java.util.Map;

public interface IDangerStatisticsVo {

	public void setDanger(Long danger);

	public void setRectification(Long rectification);

	public void setReview(Long review);

	public void setFinish(Long finish);

	public void setDangers(Map<String, Long> dangers);

	public void setFinishs(Map<String, Long> finishs);

	public void setRectifications(Map<String, Long> rectifications);

	public void setReviews(Map<String, Long> reviews);

}
