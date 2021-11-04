package seedu.mtracker.error.fileerror;

import seedu.mtracker.error.ErrorMessage;

/**
 * The custom exception class that is thrown when storage file forex exit price is not valid.
 */
public class InvalidExitPriceSavedInFileError extends Exception {

    /**
     * Returns the error message to the user stating that exit price in storage file is invalid.
     *
     * @return A string error message that states the exit price in storage file is in the wrong format.
     */
    @Override
    public String getMessage() {
        return ErrorMessage.EXIT_PRICE_FORMATTING_IN_FILE_ERROR;
    }
}
