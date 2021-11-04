package seedu.mtracker.filemanager;

import seedu.mtracker.commons.Validate;
import seedu.mtracker.commons.error.fileerror.InvalidCurrPriceSavedInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidEmptyCurrPriceInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidEmptyNameInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidEmptySentimentInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidEmptyStatusInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidInstrumentInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidNameSavedInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidSentimentSavedInFileError;
import seedu.mtracker.commons.error.fileerror.InvalidStatusSavedInFileError;
import seedu.mtracker.LogHelper;
import seedu.mtracker.model.Instrument;
import seedu.mtracker.model.InstrumentManager;
import seedu.mtracker.ui.TextUi;

import java.util.List;
import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class InstrumentDecoder {

    public static final int SPLIT_FUNCTION_LIMIT_VALUE = -1;

    public static final String TYPE_CRYPTO = "crypto";
    public static final String TYPE_STOCK = "stock";
    public static final String TYPE_ETF = "etf";
    public static final String TYPE_FOREX = "forex";

    public static final int INDEX_START = 1;

    public static final int TYPE_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int CURR_PRICE_INDEX = 2;
    public static final int SENTIMENT_INDEX = 3;
    public static final int IS_DONE_INDEX = 4;

    protected static String decodedSentiment;
    protected static String decodedName;
    protected static double decodedCurrPrice;
    protected static boolean decodedIsDone;

    protected static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    protected static final int ASCII_CODE = 127;
    protected static final char FILE_SEPARATOR = (char) ASCII_CODE;

    public static String getNameFromFile(String[] textSegment) throws InvalidEmptyNameInFileError {
        String name;
        try {
            name = textSegment[NAME_INDEX];
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidEmptyNameInFileError();
        }
        return name;
    }

    public static String getCurrPriceFromFile(String[] textSegment) throws InvalidEmptyCurrPriceInFileError {
        String currPrice;
        try {
            currPrice = textSegment[CURR_PRICE_INDEX];
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidEmptyCurrPriceInFileError();
        }
        return currPrice;
    }

    public static String getSentimentFromFile(String[] textSegment) throws InvalidEmptySentimentInFileError {
        String sentiment;
        try {
            sentiment = textSegment[SENTIMENT_INDEX];
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidEmptySentimentInFileError();
        }
        return sentiment;
    }

    public static String getDoneStatusFromFile(String[] textSegment) throws InvalidEmptyStatusInFileError {
        String status;
        try {
            status = textSegment[IS_DONE_INDEX];
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidEmptyStatusInFileError();
        }
        return status;
    }

    private static void validateGeneralAttributes(String[] textSegment) throws InvalidSentimentSavedInFileError,
            InvalidNameSavedInFileError, InvalidCurrPriceSavedInFileError, InvalidStatusSavedInFileError {
        if (!Validate.isValidName(textSegment[NAME_INDEX], textSegment[TYPE_INDEX])) {
            throw new InvalidNameSavedInFileError();
        }
        if (!Validate.isValidPrice(textSegment[CURR_PRICE_INDEX])) {
            throw new InvalidCurrPriceSavedInFileError();
        }
        if (!Validate.isValidSentiment(textSegment[SENTIMENT_INDEX])) {
            throw new InvalidSentimentSavedInFileError();
        }
        if (!Validate.isValidStatus(textSegment[IS_DONE_INDEX])) {
            throw new InvalidStatusSavedInFileError();
        }
    }

    public static void decodeGeneralAttributes(String name, String currPrice, String sentiment, String status) {
        decodedName = name;
        decodedCurrPrice = Double.parseDouble(currPrice);
        decodedSentiment = sentiment;
        decodedIsDone = Boolean.parseBoolean(status);
    }

    public static void validateAndDecodeGeneralAttributes(String[] textSegment) throws InvalidEmptyNameInFileError,
            InvalidEmptyCurrPriceInFileError, InvalidEmptySentimentInFileError, InvalidSentimentSavedInFileError,
            InvalidEmptyStatusInFileError, InvalidNameSavedInFileError, InvalidCurrPriceSavedInFileError,
            InvalidStatusSavedInFileError {
        String name = getNameFromFile(textSegment);
        String currPrice = getCurrPriceFromFile(textSegment);
        String sentiment = getSentimentFromFile(textSegment);
        String status = getDoneStatusFromFile(textSegment);
        validateGeneralAttributes(textSegment);
        decodeGeneralAttributes(name, currPrice, sentiment, status);
    }

    public static void setDoneStatus(boolean isDone, Instrument doneInstrument) {
        if (!isDone) {
            return;
        }
        doneInstrument.markAsDone();
    }

    public static void readFile(InstrumentManager instrumentManager, List<String> fileData) {
        AtomicInteger idx = new AtomicInteger(INDEX_START);
        fileData.stream()
                .forEach((line) -> {
                    String[] textSegment = line.split(String.valueOf(FILE_SEPARATOR), SPLIT_FUNCTION_LIMIT_VALUE);
                    try {
                        addSavedInstrumentToList(instrumentManager, textSegment);
                    } catch (Exception e) {
                        TextUi.showErrorMessage(e);
                        TextUi.ignoreCorruptedInstrument(idx);
                        logger.warning(LogHelper.LOG_DATA_FILE_INSTRUMENT_CORRUPTED_ERROR);
                    } finally {
                        idx.getAndIncrement();
                    }
                });
    }

    public static void addSavedInstrumentToList(InstrumentManager instrumentManager, String[] textSegment)
            throws Exception {
        switch (textSegment[TYPE_INDEX].toLowerCase()) {
        case TYPE_CRYPTO:
            CryptoDecoder.addCryptoToList(textSegment, instrumentManager);
            break;
        case TYPE_STOCK:
            StockDecoder.addStockToList(textSegment, instrumentManager);
            break;
        case TYPE_FOREX:
            ForexDecoder.addForexToList(textSegment, instrumentManager);
            break;
        case TYPE_ETF:
            EtfDecoder.addEtfToList(textSegment, instrumentManager);
            break;
        default:
            logger.warning(LogHelper.LOG_DATA_FILE_INSTRUMENT_TYPE_CORRUPTED_ERROR);
            throw new InvalidInstrumentInFileError();
        }
    }
}
