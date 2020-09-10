package fr.umlv.smalljs.grammar.tools;

import fr.umlv.tatoo.runtime.tools.ToolsTable;

import java.util.EnumMap;
import java.util.EnumSet;

import fr.umlv.smalljs.grammar.lexer.RuleEnum;
import fr.umlv.smalljs.grammar.parser.TerminalEnum;

public class ToolsDataTable {
  public static ToolsTable<RuleEnum,TerminalEnum> createToolsTable() {
      EnumSet<RuleEnum> spawns = EnumSet.of(RuleEnum.lcurl,RuleEnum.text,RuleEnum.id,RuleEnum.semicolon,RuleEnum.rcurl,RuleEnum.rpar,RuleEnum.comment,RuleEnum.eq,RuleEnum._else,RuleEnum.add,RuleEnum.comma,RuleEnum.ge,RuleEnum.function,RuleEnum.var,RuleEnum._return,RuleEnum.dot,RuleEnum.sub,RuleEnum.gt,RuleEnum.integer,RuleEnum.lt,RuleEnum.div,RuleEnum.lpar,RuleEnum.mul,RuleEnum.ne,RuleEnum.rem,RuleEnum._if,RuleEnum.colon,RuleEnum.assign,RuleEnum.le,RuleEnum.eol);
      EnumSet<RuleEnum> discards = EnumSet.allOf(RuleEnum.class);
      EnumMap<RuleEnum,TerminalEnum> terminal = new EnumMap<RuleEnum,TerminalEnum>(RuleEnum.class);
              terminal.put(RuleEnum.lcurl,TerminalEnum.lcurl);
              terminal.put(RuleEnum.text,TerminalEnum.text);
              terminal.put(RuleEnum.id,TerminalEnum.id);
              terminal.put(RuleEnum.semicolon,TerminalEnum.semicolon);
              terminal.put(RuleEnum.rcurl,TerminalEnum.rcurl);
              terminal.put(RuleEnum.rpar,TerminalEnum.rpar);
              terminal.put(RuleEnum.eq,TerminalEnum.eq);
              terminal.put(RuleEnum._else,TerminalEnum._else);
              terminal.put(RuleEnum.add,TerminalEnum.add);
              terminal.put(RuleEnum.comma,TerminalEnum.comma);
              terminal.put(RuleEnum.ge,TerminalEnum.ge);
              terminal.put(RuleEnum.function,TerminalEnum.function);
              terminal.put(RuleEnum.var,TerminalEnum.var);
              terminal.put(RuleEnum._return,TerminalEnum._return);
              terminal.put(RuleEnum.dot,TerminalEnum.dot);
              terminal.put(RuleEnum.sub,TerminalEnum.sub);
              terminal.put(RuleEnum.gt,TerminalEnum.gt);
              terminal.put(RuleEnum.integer,TerminalEnum.integer);
              terminal.put(RuleEnum.lt,TerminalEnum.lt);
              terminal.put(RuleEnum.div,TerminalEnum.div);
              terminal.put(RuleEnum.lpar,TerminalEnum.lpar);
              terminal.put(RuleEnum.mul,TerminalEnum.mul);
              terminal.put(RuleEnum.ne,TerminalEnum.ne);
              terminal.put(RuleEnum.rem,TerminalEnum.rem);
              terminal.put(RuleEnum._if,TerminalEnum._if);
              terminal.put(RuleEnum.colon,TerminalEnum.colon);
              terminal.put(RuleEnum.assign,TerminalEnum.assign);
              terminal.put(RuleEnum.le,TerminalEnum.le);
              terminal.put(RuleEnum.eol,TerminalEnum.eol);
            EnumSet<RuleEnum> unconditionals = EnumSet.of(RuleEnum.comment,RuleEnum.space);
      return new ToolsTable<RuleEnum,TerminalEnum>(spawns,discards,unconditionals,terminal);
  }
}