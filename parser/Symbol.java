package parser;

import java.util.List;

public interface Symbol {

    ParseState parse(List<Token>  input);
}