package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.email.TemplateType.*;
import static br.com.sw2you.realmeet.utils.TestDataCreator.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.*;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import br.com.sw2you.realmeet.config.properties.TemplateConfigProperties;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.service.NotificationService;
import br.com.sw2you.realmeet.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class NotificationServiceIntegrationTest extends BaseIntegrationTest {
    @Autowired
    NotificationService victim;

    @Autowired
    private TemplateConfigProperties templateConfigProperties;

    @Autowired
    private EmailConfigProperties emailConfigProperties;

    @MockBean
    private EmailSender emailSender;

    private Allocation allocation;

    @Override
    protected void setupEach() throws Exception {
        allocation = newAllocationBuilder(newRoomBuilder().build()).build();
    }

    @Test
    void testNotifyAllocationCreated() {
        victim.notifyAllocationCreated(allocation);
        testInteraction(ALLOCATION_CREATED);
    }

    @Test
    void testNotifyAllocationUpdated() {
        victim.notifyAllocationUpdated(allocation);
        testInteraction(ALLOCATION_UPDATED);
    }

    @Test
    void testNotifyAllocationDeleted() {
        victim.notifyAllocationDeleted(allocation);
        testInteraction(ALLOCATION_DELETED);
    }

    private void testInteraction(TemplateType templateType) {
        var emailTemplate = templateConfigProperties.getEmailTemplate(templateType);
        verify(emailSender)
            .send(
                argThat(
                    emailInfo ->
                        emailInfo.getSubject().equals(emailTemplate.getSubject()) &&
                        emailInfo.getTemplate().equals(emailTemplate.getTemplateName()) &&
                        emailInfo.getTo().get(0).equals(allocation.getEmployee().getEmail()) &&
                        emailInfo.getFrom().equals(emailConfigProperties.getFrom()) &&
                        emailInfo.getTemplateData().get(Constants.ALLOCATION).equals(allocation)
                )
            );
    }
}
