package seedu.mtracker.instrument.subinstrument;

import seedu.mtracker.instrument.Instrument;
import seedu.mtracker.ui.TextUi;

public class Crypto extends Instrument {

    protected String expiry;
    protected String remark;
    protected static final String CRYPTO_ICON = "C";
    protected static final String TYPE_STOCK = "Crypto";

    public Crypto(String name, double currentPrice, String sentiment, String expiry, String remark) {
        super(name, currentPrice, sentiment);
        this.expiry = expiry;
        this.remark = remark;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getRemarks() {
        return remark;
    }

    @Override
    public String toString() {
        return TextUi.createBoxDisplay(CRYPTO_ICON) + getName();
    }

    @Override
    public String getType() {
        return TYPE_STOCK;
    }

    @Override
    public String toList() {
        return super.toList()
                + System.lineSeparator() + EXPIRY_HEADER + getExpiry()
                + System.lineSeparator() + REMARKS_HEADER + getRemarks();
    }
}
