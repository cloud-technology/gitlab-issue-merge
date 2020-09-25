package com.ct.gim.clients;

import java.util.List;

import com.ct.gim.properties.gitlab.Milestone;
import com.ct.gim.properties.gitlab.ProjectDto;
import com.ct.gim.properties.gitlab.issues.Issue;
import com.ct.gim.properties.gitlab.issues.IssuesCreateDto;
import com.ct.gim.properties.gitlab.milestones.MilestonesCreateDto;
import com.ct.gim.properties.gitlab.release.ReleaseCreateDto;
import com.ct.gim.properties.gitlab.release.ReleaseCreateResultDto;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Headers;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(name = "gitlab", url = "https://gitlab.com/api/v4", configuration = GitlabClient.MultipartSupportConfig.class)
public interface GitlabClient {
        // 取得專案資訊
        @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectid}")
        ProjectDto getProject(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid);

        // 取得 Milestone
        @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectid}/milestones")
        List<Milestone> getMilestones(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid);

        // 透過 title 查詢專案有哪些 Milestone
        @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectid}/milestones")
        List<Milestone> getMilestonesByQuery(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid,
                        @SpringQueryMap MilestonesQueryParams milestonesQueryParams);

        // 透過 milestoneNumber 取得專案有哪些 Issue
        @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectid}/milestones/{milestoneNumber}/issues")
        List<Issue> getIssuesByMilestone(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid,
                        @PathVariable("milestoneNumber") Integer milestoneNumber);
        // 取得專案所有 Issues
        @RequestMapping(method = RequestMethod.GET, path = "/projects/{projectid}/issues")
        List<Issue> getIssuesByProject(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid);

        // 建立新的 releases
        @RequestMapping(method = RequestMethod.POST, path = "/projects/{projectid}/releases")
        ReleaseCreateResultDto createRelease(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid, ReleaseCreateDto releaseCreateDto);

        @Headers("Content-Type: application/x-www-form-urlencoded")
        @RequestMapping(method = RequestMethod.POST, path = "/projects/{projectid}/milestones")
        Milestone createMilestones(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid, MilestonesCreateDto milestonesCreateDto);

        @Headers("Content-Type: application/x-www-form-urlencoded")
        @RequestMapping(method = RequestMethod.POST, path = "/projects/{projectid}/issues")
        Issue createIssues(@RequestHeader("Authorization") String authorization,
                        @PathVariable("projectid") Integer projectid, IssuesCreateDto issuesCreateDto);

        public class MultipartSupportConfig {
                @Autowired
                private ObjectFactory<HttpMessageConverters> messageConverters;

                @Bean
                public Encoder feignFormEncoder() {
                        return new SpringFormEncoder(new SpringEncoder(messageConverters));
                }
        }
}