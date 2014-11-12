package org.elixir_lang.elixir_flex_lexer.sigil_modifiers.group;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.elixir_lang.ElixirFlexLexer;
import org.elixir_lang.elixir_flex_lexer.TokenTest;
import org.elixir_lang.psi.ElixirTypes;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by luke.imhoff on 10/23/14.
 */
public abstract class Test extends TokenTest {
    /*
     * Constructors
     */

    public Test(CharSequence charSequence, IElementType tokenType, int lexicalState) {
        super(charSequence, tokenType, lexicalState);
    }

    /*
     * Methods
     */

    @Parameterized.Parameters(
            name = "\"{0}\" parses as {1} token and advances to state {2}"
    )
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][]{
                        { " ", TokenType.WHITE_SPACE, ElixirFlexLexer.YYINITIAL },
                        { ";", ElixirTypes.EOL, ElixirFlexLexer.YYINITIAL },
                        { "A", ElixirTypes.ALIAS, ElixirFlexLexer.YYINITIAL },
                        { "\n", ElixirTypes.EOL, ElixirFlexLexer.YYINITIAL },
                        { "\r\n", ElixirTypes.EOL, ElixirFlexLexer.YYINITIAL },
                        { "a", ElixirTypes.SIGIL_MODIFIER, ElixirFlexLexer.SIGIL_MODIFIERS }
                }
        );
    }

    protected abstract char promoter();
    protected abstract char terminator();

    protected void reset(CharSequence charSequence) throws IOException {
        // start to trigger SIGIL_MODIFIERS after GROUP
        CharSequence fullCharSequence = "~r" + promoter() + terminator() + charSequence;
        super.reset(fullCharSequence);
        // consume ~
        flexLexer.advance();
        // consume r
        flexLexer.advance();
        // consume promoter
        flexLexer.advance();
        // consume terminator
        flexLexer.advance();
    }
}
