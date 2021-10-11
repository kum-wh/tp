package seedu.mtracker.console;

import seedu.mtracker.commands.AddEtfCommand;
import seedu.mtracker.commands.AddInstrumentCommand;
import seedu.mtracker.ui.TextUi;

public class AddEtfParser extends AddInstrumentParser {

    public static String INSTRUMENT_TYPE = "etf";

    private static final double UNDEFINED_PAST_RETURN_VALUE = -101;

    public String getEtfRemarkFromUser() {
        TextUi.displayAddRemarksInstruction();
        return getUserInput();
    }

    public String getEtfPastReturnFromUser() {
        TextUi.displayAddPastReturnsInstruction();
        String userInput = getUserInput();
        double pastReturn = isValidPastReturn(userInput);
        return String.valueOf(pastReturn);
    }

    public static double isValidPastReturn(String userInput) {
        double pastReturn;
        try {
            pastReturn = Double.parseDouble(userInput);
        } catch (NumberFormatException e) {
            pastReturn = UNDEFINED_PAST_RETURN_VALUE;
        }
        return pastReturn;
    }

    public void addEtfRemarkToParameters() {
        String remarks = getEtfRemarkFromUser();
        parameters.add(remarks);
    }

    public void addEtfPastReturnToParameters() {
        String pastReturns = getEtfPastReturnFromUser();
        parameters.add(pastReturns);
    }

    public void getEtfSpecificParameters() {
        addEtfPastReturnToParameters();
        addEtfRemarkToParameters();
    }

    @Override
    public AddInstrumentCommand getInstrumentParameters() {
        getGeneralParameters(INSTRUMENT_TYPE);
        getEtfSpecificParameters();
        return new AddEtfCommand();
    }
}
