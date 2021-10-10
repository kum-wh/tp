package seedu.mtracker.instrument.subinstrument;

import seedu.mtracker.instrument.Instrument;
import seedu.mtracker.ui.TextUi;

public class Forex extends Instrument {
    protected double entry;
    protected double exit;
    protected String expiry;
    protected String remarks;

    private static final String FX_ICON = "F";

    public Forex(
            String name,
            double currentPrice,
            String sentiment,
            double entryPrice,
            double exitPrice,
            String deadline,
            String remark
    ) {
        super(name, currentPrice, sentiment);
        this.entryPrice = entryPrice;
        this.exitPrice = exitPrice;
        this.expiry = expiry;
        this.remark = remark;

    }

    @Override
    public String toString() {
        return TextUi.createBoxDisplay(FX_ICON) + getName();
    }
}
