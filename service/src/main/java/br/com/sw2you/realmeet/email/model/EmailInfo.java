package br.com.sw2you.realmeet.email.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmailInfo {
    private final String from;
    private final List<String> to;
    private final List<String> cc;
    private final List<String> bcc;
    private final String subject;
    private final List<Attachment> attachments;
    private final String template;
    private final Map<String, Object> templateData;

    private EmailInfo(Builder builder) {
        from = builder.from;
        to = builder.to;
        cc = builder.cc;
        bcc = builder.bcc;
        subject = builder.subject;
        attachments = builder.attachments;
        template = builder.template;
        templateData = builder.templateData;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public String getTemplate() {
        return template;
    }

    public Map<String, Object> getTemplateData() {
        return templateData;
    }

    @Override
    public String toString() {
        return (
            "EmailInfo{" +
            "from='" +
            from +
            '\'' +
            ", to=" +
            to +
            ", cc=" +
            cc +
            ", bcc=" +
            bcc +
            ", subject='" +
            subject +
            '\'' +
            ", attachments=" +
            attachments +
            ", template='" +
            template +
            '\'' +
            ", templateData=" +
            templateData +
            '}'
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailInfo emailInfo = (EmailInfo) o;
        return (
            from.equals(emailInfo.from) &&
            to.equals(emailInfo.to) &&
            cc.equals(emailInfo.cc) &&
            bcc.equals(emailInfo.bcc) &&
            subject.equals(emailInfo.subject) &&
            attachments.equals(emailInfo.attachments) &&
            template.equals(emailInfo.template) &&
            templateData.equals(emailInfo.templateData)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, cc, bcc, subject, attachments, template, templateData);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String from;
        private List<String> to;
        private List<String> cc;
        private List<String> bcc;
        private String subject;
        private List<Attachment> attachments;
        private String template;
        private Map<String, Object> templateData;

        private Builder() {}

        public Builder withFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder withTo(List<String> to) {
            this.to = to;
            return this;
        }

        public Builder withCc(List<String> cc) {
            this.cc = cc;
            return this;
        }

        public Builder withBcc(List<String> bcc) {
            this.bcc = bcc;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withAttachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Builder withTemplate(String template) {
            this.template = template;
            return this;
        }

        public Builder withTemplateData(Map<String, Object> templateData) {
            this.templateData = templateData;
            return this;
        }

        public EmailInfo build() {
            return new EmailInfo(this);
        }
    }
}
