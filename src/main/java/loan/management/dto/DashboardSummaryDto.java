package loan.management.dto;

import java.util.List;

public class DashboardSummaryDto {
    public long totalLoan;
    public long pendingApproval;
    public long approvedLoan;
    public long growthRate;
    public long activeUser;
    public String avgProcessingTime;
    public List<ActivityDto> recentActivities;
}