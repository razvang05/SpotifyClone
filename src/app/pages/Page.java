package app.pages;

import fileio.input.CommandInput;

public class Page {

    interface PrintPage {
       String printCurrentPage(CommandInput commandInput);
    }
}
