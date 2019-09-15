package fr.umlv.smalljs.grammar.tools;

import fr.umlv.tatoo.runtime.tools.ToolsTable;

import java.util.EnumMap;
import java.util.EnumSet;

import fr.umlv.smalljs.grammar.lexer.RuleEnum;
import fr.umlv.smalljs.grammar.parser.TerminalEnum;

public class ToolsDataTable {
  public static ToolsTable<RuleEnum,TerminalEnum> createToolsTable() {
      EnumSet<RuleEnum> spawns = EnumSet.of(RuleEnum.id,RuleEnum.rcurl,RuleEnum.dot,RuleEnum.div,RuleEnum.comma,RuleEnum.var,RuleEnum.rem,RuleEnum.lpar,RuleEnum.rpar,RuleEnum.le,RuleEnum.function,RuleEnum.mul,RuleEnum.eol,RuleEnum.integer,RuleEnum.colon,RuleEnum.eq,RuleEnum.assign,RuleEnum._else,RuleEnum.lt,RuleEnum._if,RuleEnum.text,RuleEnum.ge,RuleEnum.add,RuleEnum.comment,RuleEnum.sub,RuleEnum.ne,RuleEnum.lcurl,RuleEnum.semicolon,RuleEnum._return,RuleEnum.gt);
      EnumSet<RuleEnum> discards = EnumSet.allOf(RuleEnum.class);
      EnumMap<RuleEnum,TerminalEnum> terminal = new EnumMap<RuleEnum,TerminalEnum>(RuleEnum.class);
              terminal.put(RuleEnum.id,TerminalEnum.id);
              terminal.put(RuleEnum.rcurl,TerminalEnum.rcurl);
              terminal.put(RuleEnum.dot,TerminalEnum.dot);
              terminal.put(RuleEnum.div,TerminalEnum.div);
              terminal.put(RuleEnum.comma,TerminalEnum.comma);
              terminal.put(RuleEnum.var,TerminalEnum.var);
              terminal.put(RuleEnum.rem,TerminalEnum.rem);
              terminal.put(RuleEnum.lpar,TerminalEnum.lpar);
              terminal.put(RuleEnum.rpar,TerminalEnum.rpar);
              terminal.put(RuleEnum.le,TerminalEnum.le);
              terminal.put(RuleEnum.function,TerminalEnum.function);
              terminal.put(RuleEnum.mul,TerminalEnum.mul);
              terminal.put(RuleEnum.eol,TerminalEnum.eol);
              terminal.put(RuleEnum.integer,TerminalEnum.integer);
              terminal.put(RuleEnum.colon,TerminalEnum.colon);
              terminal.put(RuleEnum.eq,TerminalEnum.eq);
              terminal.put(RuleEnum.assign,TerminalEnum.assign);
              terminal.put(RuleEnum._else,TerminalEnum._else);
              terminal.put(RuleEnum.lt,TerminalEnum.lt);
              terminal.put(RuleEnum._if,TerminalEnum._if);
              terminal.put(RuleEnum.text,TerminalEnum.text);
              terminal.put(RuleEnum.ge,TerminalEnum.ge);
              terminal.put(RuleEnum.add,TerminalEnum.add);
              terminal.put(RuleEnum.sub,TerminalEnum.sub);
              terminal.put(RuleEnum.ne,TerminalEnum.ne);
              terminal.put(RuleEnum.lcurl,TerminalEnum.lcurl);
              terminal.put(RuleEnum.semicolon,TerminalEnum.semicolon);
              terminal.put(RuleEnum._return,TerminalEnum._return);
              terminal.put(RuleEnum.gt,TerminalEnum.gt);
            EnumSet<RuleEnum> unconditionals = EnumSet.of(RuleEnum.space,RuleEnum.comment);
      return new ToolsTable<RuleEnum,TerminalEnum>(spawns,discards,unconditionals,terminal);
  }
}