//package com.ltts.usermanagement.UMrepo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.ltts.usermanagement.UMentity.DashboardTimeStamp;
//
//public interface DashboardTimeStampRepository extends JpaRepository<DashboardTimeStamp, Long> {
//	@Query("SELECT d FROM DashboardTimeStamp d WHERE d.exitTime IS NULL ORDER BY d.entryTime DESC")
//    DashboardTimeStamp findTopByExitTimeIsNullOrderByEntryTimeDesc();
//}