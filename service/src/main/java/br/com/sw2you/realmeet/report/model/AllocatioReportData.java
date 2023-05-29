package br.com.sw2you.realmeet.report.model;

import java.time.LocalDate;
import java.util.Objects;

public class AllocatioReportData extends AbstractReportData {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public AllocatioReportData(Builder builder) {
        super(builder.email);
        dateFrom = builder.dateFrom;
        dateTo = builder.dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AllocatioReportData that = (AllocatioReportData) o;
        return dateFrom.equals(that.dateFrom) && dateTo.equals(that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "AllocatioDataReport{dateFrom=" + dateFrom + ", dateTo=" + dateTo + "}";
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String email;
        private LocalDate dateFrom;
        private LocalDate dateTo;

        private Builder() {}

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withDateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public Builder withDateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public AllocatioReportData build() {
            return new AllocatioReportData(this);
        }
    }
}
