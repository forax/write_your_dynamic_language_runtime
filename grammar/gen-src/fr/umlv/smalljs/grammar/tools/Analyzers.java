package fr.umlv.smalljs.grammar.tools;

import java.io.InputStreamReader;
import java.io.Reader;

import fr.umlv.smalljs.grammar.lexer.RuleEnum;
import fr.umlv.smalljs.grammar.parser.TerminalEnum;
import fr.umlv.smalljs.grammar.parser.NonTerminalEnum;
import fr.umlv.smalljs.grammar.parser.ProductionEnum;
import fr.umlv.smalljs.grammar.parser.VersionEnum;
import fr.umlv.smalljs.grammar.lexer.LexerDataTable;
import fr.umlv.smalljs.grammar.parser.ParserDataTable;
import fr.umlv.smalljs.grammar.tools.ToolsDataTable;
import fr.umlv.smalljs.grammar.tools.GrammarEvaluator;
import fr.umlv.smalljs.grammar.tools.TerminalEvaluator;

import fr.umlv.tatoo.runtime.buffer.LexerBuffer;
import fr.umlv.tatoo.runtime.buffer.TokenBuffer;
import fr.umlv.tatoo.runtime.buffer.impl.LocationTracker;
import fr.umlv.tatoo.runtime.buffer.impl.ReaderWrapper;
import fr.umlv.tatoo.runtime.lexer.LexerTable;
import fr.umlv.tatoo.runtime.parser.ParserTable;
import fr.umlv.tatoo.runtime.tools.DataViewer;
import fr.umlv.tatoo.runtime.tools.Debug;
import fr.umlv.tatoo.runtime.tools.SemanticStack;
import fr.umlv.tatoo.runtime.tools.ToolsTable;
import fr.umlv.tatoo.runtime.tools.builder.Builder;

/** Helper methods that can be used to run a couple lexer/parser on a text.
 *
 *  This class is generated - please do not edit it 
 */
public class Analyzers {
  /**
   * Runs the analyzer (lexer+parser) on a reader and print recognized tokens and
   * applied parser rules on error input (see {@link Debug}).
   * @param reader the source of standard input if null
   * @param terminalEvaluator the terminal evaluator or just method call printer if null
   * @param grammarEvaluator the grammar evaluator or just method call printer if null
   * @param start the start or default start if null
   * @param version the version of default version if null
   */
  public static void runDebug(Reader reader,
    TerminalEvaluator<? super CharSequence> terminalEvaluator,
    GrammarEvaluator grammarEvaluator,
    NonTerminalEnum start,
    VersionEnum version) {
    if (reader==null)
      reader=new InputStreamReader(System.in);
    @SuppressWarnings("unchecked") TerminalEvaluator<CharSequence> debugTerminalEvaluator =
      Debug.createTraceProxy(TerminalEvaluator.class,terminalEvaluator);
    GrammarEvaluator debugGrammarEvaluator = Debug.createTraceProxy(GrammarEvaluator.class,grammarEvaluator);
    run(reader,debugTerminalEvaluator,debugGrammarEvaluator,
        start,version);
  }

  /** Runs the analyzer (lexer+parser) on a reader and sends recognized tokens
   *  as CharSequence. Tokens are transformed to objects by the terminal evaluator.
   *  At last, the grammar evaluator is called with these objects.
   *  
   *  This implementation uses a {@link fr.umlv.tatoo.runtime.buffer.impl.ReaderWrapper}
   *  configured with a location tracker as buffer and calls.
   *  
   * @param reader a reader used to obtain the characters of the text to parse.
   * @param terminalEvaluator an interface that returns the value of a token.
   * @param grammarEvaluator an interface that evaluates the grammar productions. 
   * @param start a start non terminal of the grammar used as root state of the parser.
   *    If start is null,
   *    the {@link fr.umlv.tatoo.runtime.parser.ParserTable#getDefaultStart() default start}
   *    non terminal is used.
   * @param version a version of the grammar used to parse the reader.
   *    If version is null,
   *    the {@link fr.umlv.tatoo.runtime.parser.ParserTable#getDefaultVersion() default version}
   *    of the grammar is used.
   *    
   * @see #run(TokenBuffer, TerminalEvaluator, GrammarEvaluator, NonTerminalEnum, VersionEnum)
   */
  public static void run(
    Reader reader,
    TerminalEvaluator<? super CharSequence> terminalEvaluator,
    GrammarEvaluator grammarEvaluator,
    NonTerminalEnum start,
    VersionEnum version) {

    run(new ReaderWrapper(reader, new LocationTracker()), terminalEvaluator, grammarEvaluator, start, version);
  }
  
  public static <B extends TokenBuffer<D>&LexerBuffer,D> void runDebug(
    B tokenBuffer,
    TerminalEvaluator<? super D> terminalEvaluator,
    GrammarEvaluator grammarEvaluator,
    NonTerminalEnum start,
    VersionEnum version) {
      @SuppressWarnings("unchecked") TerminalEvaluator<? super D> debugTerminalEvaluator =
      Debug.createTraceProxy(TerminalEvaluator.class,terminalEvaluator);
    GrammarEvaluator debugGrammarEvaluator = Debug.createTraceProxy(GrammarEvaluator.class,grammarEvaluator);
    run(tokenBuffer,debugTerminalEvaluator,debugGrammarEvaluator,
        start,version);
    }
  
  /** Runs the analyzer (lexer+parser) on a token buffer and sends recognized tokens
   *  as CharSequence. Tokens are transformed to objects by the terminal evaluator.
   *  At last, the grammar evaluator is called with these objects.
   *  
   *  It is up to the caller to create its buffer and to provide or not a location tracker.
   *
   * @param <B> type of the buffer.
   *  
   * @param tokenBuffer the token buffer used to obtain the characters of the text to parse.
   * @param terminalEvaluator an interface that returns the value of a token.
   * @param grammarEvaluator an interface that evaluates the grammar productions. 
   * @param start a start non terminal of the grammar used as root state of the parser.
   *    If start is null,
   *    the {@link fr.umlv.tatoo.runtime.parser.ParserTable#getDefaultStart() default start}
   *    non terminal is used.
   * @param version a version of the grammar used to parse the reader.
   *    If version is null,
   *    the {@link fr.umlv.tatoo.runtime.parser.ParserTable#getDefaultVersion() default version}
   *    of the grammar is used.
   *
   * @see #run(Reader, TerminalEvaluator, GrammarEvaluator, NonTerminalEnum, VersionEnum)
   */
  public static <B extends TokenBuffer<D>&LexerBuffer,D> void run(
    B tokenBuffer,
    TerminalEvaluator<? super D> terminalEvaluator,
    GrammarEvaluator grammarEvaluator,
    NonTerminalEnum start,
    VersionEnum version) {
  
    analyzerTokenBufferBuilder(tokenBuffer,terminalEvaluator,grammarEvaluator,new SemanticStack()).
      start(start).version(version).createLexer().run();
  }

  public static Builder.LexerTableBuilder<RuleEnum> lexerBuilder() {
    return Builder.lexer(LexerDataTable.createTable());
  }
  
  public static Builder.ParserTableBuilder<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> parserBuilder() {
    return Builder.parser(ParserDataTable.createTable());
  }
  
  public static Builder.AnalyzerTableBuilder<RuleEnum,TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> analyzerBuilder() {
    return Builder.analyzer(LEXER_TABLE,PARSER_TABLE,TOOLS_TABLE);
  }
  
  public static <B extends LexerBuffer> Builder.AnalyzerBuilder<RuleEnum,B,TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> analyzerLexerBufferBuilder(B lexerBuffer,
    TerminalEvaluator<? super B> terminalEvaluator, GrammarEvaluator grammarEvaluator,
    SemanticStack semanticStack) {
    return analyzerBuilder().buffer(lexerBuffer).listener(AnalyzerProcessor.<B,B>createAnalyzerProcessor(terminalEvaluator,grammarEvaluator,
      DataViewer.<B>getIdentityDataViewer(),semanticStack));
  }
  
  public static <B extends TokenBuffer<D>&LexerBuffer,D> Builder.AnalyzerBuilder<RuleEnum,B,TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> analyzerTokenBufferBuilder(B tokenBuffer,
    TerminalEvaluator<? super D> terminalEvaluator, GrammarEvaluator grammarEvaluator,
    SemanticStack semanticStack) {
    return analyzerBuilder().buffer(tokenBuffer).listener(AnalyzerProcessor.<B,D>createAnalyzerProcessor(terminalEvaluator,grammarEvaluator,
      DataViewer.<D>getTokenBufferViewer(),semanticStack));
  }
  
  private static final LexerTable<RuleEnum> LEXER_TABLE;
  private static final ParserTable<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> PARSER_TABLE;
  private static final ToolsTable<RuleEnum,TerminalEnum> TOOLS_TABLE;

  static {
    LEXER_TABLE = LexerDataTable.createTable();
    PARSER_TABLE = ParserDataTable.createTable();
    TOOLS_TABLE = ToolsDataTable.createToolsTable();
  }

  /* sample main method
  
  public static void main(String[] args) throws java.io.IOException {
    java.io.Reader reader;
    if (args.length>0) {
      reader = new java.io.FileReader(args[0]);
    } else {
      reader = new java.io.InputStreamReader(System.in);
    }
    //TODO implements the terminal attribute evaluator here
    TerminalEvaluator<CharSequence> terminalEvaluator = fr.umlv.tatoo.runtime.tools.Debug.createTraceProxy(TerminalEvaluator.class);

    //TODO implements the grammar evaluator here
    GrammarEvaluator grammarEvaluator = fr.umlv.tatoo.runtime.tools.Debug.createTraceProxy(GrammarEvaluator.class);

    //TODO choose a start non terminal and a version here
    VersionEnum version = VersionEnum.DEFAULT;
    NonTerminalEnum start = NonTerminalEnum.script;

    Analyzers.run(reader, terminalEvaluator, grammarEvaluator, start, version);
  }*/
}
