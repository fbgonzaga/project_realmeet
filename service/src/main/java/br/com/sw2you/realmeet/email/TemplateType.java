package br.com.sw2you.realmeet.email;

public enum TemplateType {
    ALLOCATION_CREATED("allocationCreated"),
    ALLOCATION_DELETED("allocationDeleted"),
    ALLOCATION_UPDATED("allocationUpdated"),
    ALLOCATION_REPORT("allocationReport");

    final String templateName;

    TemplateType(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
