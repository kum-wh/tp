package seedu.mtracker.console;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEtfParserTest {

    public static final int PARAMETER_SIZE = 5;
    private static final String SEPARATOR_SPECIFIERS = "%1$s";

    public static final String USER_INPUT_NO_REMARKS = "TTTXXX"
            + SEPARATOR_SPECIFIERS + "23.4"
            + SEPARATOR_SPECIFIERS + "positive"
            + SEPARATOR_SPECIFIERS + "50.0"
            + SEPARATOR_SPECIFIERS + " ";

    public static final String USER_INPUT_All_PARAM = "TTTXXX"
            + SEPARATOR_SPECIFIERS + "23.4"
            + SEPARATOR_SPECIFIERS + "positive"
            + SEPARATOR_SPECIFIERS + "50.0"
            + SEPARATOR_SPECIFIERS + "fooRemarks";

    public static final String[] EXPECTED_PARAMS_NO_REMARKS = {
            "TTTXXX",
            "23.4",
            "positive",
            "50.0",
            "",
    };

    public static final String[] EXPECTED_PARAMS_ALL_PARAM = {
            "TTTXXX",
            "23.4",
            "positive",
            "50.0",
            "fooRemarks"
    };

    public static final String USER_INPUT_TRY_INVALID_NAME = SEPARATOR_SPECIFIERS.repeat(2) + "TTTXXX"
            + SEPARATOR_SPECIFIERS + "23.4"
            + SEPARATOR_SPECIFIERS + "positive"
            + SEPARATOR_SPECIFIERS + "50.0"
            + SEPARATOR_SPECIFIERS + "fooRemarks";


    public static final String USER_INPUT_TRY_INVALID_PRICE = SEPARATOR_SPECIFIERS + "TTTXXX"
            + SEPARATOR_SPECIFIERS + "2sd3.4"
            + SEPARATOR_SPECIFIERS + "23.4"
            + SEPARATOR_SPECIFIERS + "positive"
            + SEPARATOR_SPECIFIERS + "50.0"
            + SEPARATOR_SPECIFIERS + "fooRemarks";

    public static final String USER_INPUT_TRY_INVALID_SENTIMENT = SEPARATOR_SPECIFIERS + "TTTXXX"
            + SEPARATOR_SPECIFIERS + "23.4"
            + SEPARATOR_SPECIFIERS + "foobar"
            + SEPARATOR_SPECIFIERS.repeat(2) + "positive"
            + SEPARATOR_SPECIFIERS + "50.0"
            + SEPARATOR_SPECIFIERS + "fooRemarks";


    public static final String USER_INPUT_TRY_INVALID_RETURNS = SEPARATOR_SPECIFIERS + "TTTXXX"
            + SEPARATOR_SPECIFIERS + "23.4"
            + SEPARATOR_SPECIFIERS + "positive"
            + SEPARATOR_SPECIFIERS + ""
            + SEPARATOR_SPECIFIERS + "fooRemarks";

    public static final String[] EXPECTED_PARAMS_INVALID_RETURNS = {
            "TTTXXX",
            "23.4",
            "positive",
            "-101.0",
            "fooRemarks" };

    String formatConsoleInput(String input) {
        return String.format(input, System.lineSeparator());
    }

    void simulateConsoleInput(String input) {
        String formattedInput = formatConsoleInput(input);
        ByteArrayInputStream inputStreamBytes = new ByteArrayInputStream(formattedInput.getBytes());
        System.setIn(inputStreamBytes);
    }

    void checkParameters(AddEtfParser testEtfParser, String[] expectedParameters) {
        for (int i = 0; i < PARAMETER_SIZE; i++) {
            assertEquals(testEtfParser.getParameters().get(i), expectedParameters[i]);
        }
    }

    void testEtfParameters(String input, String[] expectedParameters) {
        simulateConsoleInput(input);
        AddEtfParser testEtfParser = new AddEtfParser();
        testEtfParser.initParameters();
        testEtfParser.getInstrumentParameters();
        checkParameters(testEtfParser, expectedParameters);
    }

    @Test
    void addEtfParams_noRemarks_expectSuccess() {
        testEtfParameters(USER_INPUT_NO_REMARKS, EXPECTED_PARAMS_NO_REMARKS);
    }

    @Test
    void addEtfParams_noPastReturn_expectSuccess() {
        testEtfParameters(USER_INPUT_TRY_INVALID_RETURNS, EXPECTED_PARAMS_INVALID_RETURNS);
    }

    @Test
    void addEtfParams_allParameters_expectSuccess() {
        testEtfParameters(USER_INPUT_All_PARAM, EXPECTED_PARAMS_ALL_PARAM);
    }

    @Test
    void addEftParams_InvalidName_expectSuccess() {
        testEtfParameters(USER_INPUT_TRY_INVALID_NAME, EXPECTED_PARAMS_ALL_PARAM);
    }

    @Test
    void addEftParams_InvalidPrice_expectSuccess() {
        testEtfParameters(USER_INPUT_TRY_INVALID_PRICE, EXPECTED_PARAMS_ALL_PARAM);
    }

    @Test
    void addEtfParams_InvalidSentiment_expectSuccess() {
        testEtfParameters(USER_INPUT_TRY_INVALID_SENTIMENT, EXPECTED_PARAMS_ALL_PARAM);
    }
}
