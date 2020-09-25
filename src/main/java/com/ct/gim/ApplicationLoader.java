package com.ct.gim;

import com.ct.gim.generator.NotesGenerator;
import com.ct.gim.generator.ReleaseNotesGenerator;
import com.ct.gim.properties.ApplicationProperties;
import com.ct.gim.properties.gitlab.Gitlab;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationLoader {
    @NonNull
    private final ApplicationProperties applicationProperties;
    @NonNull
    private final ReleaseNotesGenerator releaseNotesGenerator;
    @NonNull
    private final NotesGenerator notesGenerator;

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        Gitlab gitlab = applicationProperties.getGitlab();
        try {            
            if(gitlab == null){
                throw new IllegalAccessException("Gitlab 設定檔不存在");
            }
            notesGenerator.generate(gitlab);
            // releaseNotesGenerator.generate(gitlab.getMilestoneTitle(), gitlab.getPathToGenerateFile());
        } catch (Exception e) {
            log.error("執行發生錯誤", e);
        }
    }
}
