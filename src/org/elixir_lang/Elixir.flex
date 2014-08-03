package org.elixir_lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.elixir_lang.psi.ElixirTypes;
import com.intellij.psi.TokenType;

%%

%class ElixirLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

EOL = \n|\r|\r\n
WHITE_SPACE=[\ \t\f]

COMMENT = "#" [^\r\n]*

%%

<YYINITIAL> {
  {COMMENT} { return ElixirTypes.COMMENT; }
}

{EOL}          { return ElixirTypes.EOL; }
{WHITE_SPACE}+ { return TokenType.WHITE_SPACE; }
.              { return TokenType.BAD_CHARACTER; }