package com.github.alxwhtmr.muw.common;


/**
 * Created on 12.09.2014.
 */
public class Common {
    public class Constants {
        public class Hints {
            public static final String NAME_HINT = "HINT: #name <name>\n";
            public static final String EG = "(e.g. #connect 192.168.30 8787)\n";
            public static final String CONNECT_HINT = "#connect <host> <port> " + EG;
            public static final String DISCONNECT_HINT = "#disconnect\n";
            public static final String INIT_MSG = NAME_HINT + CONNECT_HINT + DISCONNECT_HINT;
            public static final String USAGE = "Usage: #connect <host name> <port number>.";
        }

        public class Service {
            public static final String DISCONNECTED = "Disconnected\n";
            public static final String ALREADY_CONNECTED = "Connection already established.";
            public static final String QUIT = "quit";
            public static final char SERVICE_SYMBOL = '#';
        }

        public class Channels {
            public static final String GLOBAL = "бол ";
        }

        public class Misc {
            public static final String HOST = "localhost";
            public static final int PORT = 8787;
        }
    }

    public class Patterns {
        public class Service {
            public static final String QUIT_PATTERN = "(?i)quit|disconnect|close";
            public static final String CONNECT = "(?i)connect";
            public static final String CLEAR = "(?i)clear|clearscr|refresh";
            public static final String SET_NAME = "(?i)name|setname|myname|имя";

        }
        public class Channels {
            public static final String GLOBAL = "(?i)бол(т?|та|тат|тать)";
        }
        public class Symbols {
            public static final String WHITESPACE = " ";
        }
    }

    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    public static String convertTo1251(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("Windows-1251"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}
