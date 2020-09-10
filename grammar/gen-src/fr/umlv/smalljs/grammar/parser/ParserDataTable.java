package fr.umlv.smalljs.grammar.parser;

import fr.umlv.smalljs.grammar.parser.NonTerminalEnum;
import fr.umlv.smalljs.grammar.parser.ProductionEnum;
import fr.umlv.smalljs.grammar.parser.TerminalEnum;
import fr.umlv.tatoo.runtime.parser.AcceptAction;
import fr.umlv.tatoo.runtime.parser.Action;
import fr.umlv.tatoo.runtime.parser.BranchAction;
import fr.umlv.tatoo.runtime.parser.ErrorAction;
import fr.umlv.tatoo.runtime.parser.ExitAction;
import fr.umlv.tatoo.runtime.parser.ParserTable;
import fr.umlv.tatoo.runtime.parser.ReduceAction;
import fr.umlv.tatoo.runtime.parser.ShiftAction;
import fr.umlv.tatoo.runtime.parser.StateMetadata;
import java.util.EnumMap;

/** 
 *  This class is generated - please do not edit it 
 */
public class ParserDataTable {
  private ParserDataTable() {
   accept = AcceptAction.<TerminalEnum,ProductionEnum,VersionEnum>getInstance();
   exit = ExitAction.<TerminalEnum,ProductionEnum,VersionEnum>getInstance();
    initeol_optional_3Gotoes();
    initinstrGotoes();
    initinit_star_6_subGotoes();
    initscriptGotoes();
    initinit_star_6Gotoes();
    initexpr_star_2Gotoes();
    initid_star_5Gotoes();
    initid_star_5_subGotoes();
    initexpr_star_2_subGotoes();
    initblockGotoes();
    initinitGotoes();
    initinstr_star_0Gotoes();
    initinstr_star_1Gotoes();
    initexprGotoes();
    initargsGotoes();
    initid_optional_4Gotoes();
    initeoiGotoes();
    reduceid_optional_4_id = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.id_optional_4_id,1,id_optional_4Gotoes);
    reduceexpr_parens = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_parens,3,exprGotoes);
    reduceexpr_ge = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_ge,3,exprGotoes);
    reduceid_star_5_rec = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.id_star_5_rec,3,id_star_5_subGotoes);
    reduceexpr_add = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_add,3,exprGotoes);
    reduceinstr_star_1_empty = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_star_1_empty,0,instr_star_1Gotoes);
    reduceexpr_star_2_element = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_star_2_element,1,expr_star_2_subGotoes);
    reduceexpr_new = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_new,3,exprGotoes);
    reduceexpr_mul = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_mul,3,exprGotoes);
    reduceinit = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.init,4,initGotoes);
    reduceinstr_field_assign = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_field_assign,6,instrGotoes);
    reduceexpr_lt = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_lt,3,exprGotoes);
    reduceinit_star_6_through = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.init_star_6_through,1,init_star_6Gotoes);
    reduceeol_optional_3_empty = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.eol_optional_3_empty,0,eol_optional_3Gotoes);
    reduceexpr_field_access = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_field_access,3,exprGotoes);
    reduceexpr_star_2_through = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_star_2_through,1,expr_star_2Gotoes);
    reduceid_optional_4_empty = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.id_optional_4_empty,0,id_optional_4Gotoes);
    reduceexpr_id = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_id,1,exprGotoes);
    reduceinit_star_6_element = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.init_star_6_element,1,init_star_6_subGotoes);
    reduceblock = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.block,3,blockGotoes);
    reduceexpr_gt = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_gt,3,exprGotoes);
    reduceexpr_rem = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_rem,3,exprGotoes);
    reduceargs = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.args,1,argsGotoes);
    reduceexpr_le = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_le,3,exprGotoes);
    reduceexpr_method_call = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_method_call,6,exprGotoes);
    reduceinit_star_6_empty = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.init_star_6_empty,0,init_star_6Gotoes);
    reduceeoi__semicolon = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.eoi__semicolon,1,eoiGotoes);
    reduceexpr_ne = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_ne,3,exprGotoes);
    reduceeoi__eol = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.eoi__eol,1,eoiGotoes);
    reduceinstr_if = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_if,5,instrGotoes);
    reduceexpr_numeric = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_numeric,1,exprGotoes);
    reduceinstr_star_1_rec = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_star_1_rec,2,instr_star_1Gotoes);
    reduceid_star_5_through = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.id_star_5_through,1,id_star_5Gotoes);
    reduceexpr_div = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_div,3,exprGotoes);
    reduceeol_optional_3_eol = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.eol_optional_3_eol,1,eol_optional_3Gotoes);
    reduceexpr_apply = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_apply,4,exprGotoes);
    reduceexpr_star_2_empty = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_star_2_empty,0,expr_star_2Gotoes);
    reduceexpr_function_creation = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_function_creation,6,exprGotoes);
    reduceexpr_eq = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_eq,3,exprGotoes);
    reduceexpr_sub = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_sub,3,exprGotoes);
    reduceinstr_star_0_empty = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_star_0_empty,0,instr_star_0Gotoes);
    reduceinit_star_6_rec = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.init_star_6_rec,3,init_star_6_subGotoes);
    reduceinstr_var_decl = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_var_decl,5,instrGotoes);
    reduceinstr_star_0_rec = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_star_0_rec,2,instr_star_0Gotoes);
    reduceinstr_return = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_return,3,instrGotoes);
    reduceid_star_5_element = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.id_star_5_element,1,id_star_5_subGotoes);
    reduceinstr_expr = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_expr,2,instrGotoes);
    reducescript = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.script,1,scriptGotoes);
    reduceexpr_star_2_rec = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_star_2_rec,3,expr_star_2_subGotoes);
    reduceinstr_var_assign = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.instr_var_assign,4,instrGotoes);
    reduceexpr_text = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.expr_text,1,exprGotoes);
    reduceid_star_5_empty = new ReduceAction<TerminalEnum,ProductionEnum,VersionEnum>(ProductionEnum.id_star_5_empty,0,id_star_5Gotoes);
    shift81 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(81);
    shift14 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(14);
    shift61 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(61);
    shift85 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(85);
    shift84 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(84);
    shift45 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(45);
    shift41 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(41);
    shift37 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(37);
    shift39 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(39);
    shift92 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(92);
    shift47 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(47);
    shift12 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(12);
    shift26 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(26);
    shift68 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(68);
    shift49 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(49);
    shift24 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(24);
    shift55 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(55);
    shift78 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(78);
    shift29 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(29);
    shift22 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(22);
    shift74 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(74);
    shift6 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(6);
    shift17 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(17);
    shift53 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(53);
    shift20 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(20);
    shift18 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(18);
    shift66 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(66);
    shift3 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(3);
    shift7 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(7);
    shift2 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(2);
    shift51 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(51);
    shift35 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(35);
    shift43 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(43);
    shift32 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(32);
    shift64 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(64);
    shift13 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(13);
    shift69 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(69);
    shift30 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(30);
    shift16 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(16);
    shift19 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(19);
    shift15 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(15);
    shift93 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(93);
    shift4 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(4);
    shift28 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(28);
    shift10 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(10);
    shift9 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(9);
    shift77 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(77);
    shift71 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(71);
    shift57 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(57);
    shift86 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(86);
    shift76 = new ShiftAction<TerminalEnum,ProductionEnum,VersionEnum>(76);
    error0 = new ErrorAction<TerminalEnum,ProductionEnum,VersionEnum>("parse error");
    branch0 = new BranchAction<TerminalEnum,ProductionEnum,VersionEnum>("parse error");
    init__eof__Array();
    initfunctionArray();
    initgtArray();
    initsemicolonArray();
    initltArray();
    init_ifArray();
    initdotArray();
    initgeArray();
    initneArray();
    init_returnArray();
    initmulArray();
    initlcurlArray();
    initleArray();
    initeqArray();
    initvarArray();
    initintegerArray();
    initaddArray();
    initdivArray();
    initrparArray();
    initidArray();
    initcommaArray();
    initremArray();
    init_elseArray();
    inittextArray();
    initcolonArray();
    initassignArray();
    initeolArray();
    initlparArray();
    initsubArray();
    initrcurlArray();
    EnumMap<TerminalEnum,Action<TerminalEnum,ProductionEnum,VersionEnum>[]> tableMap =
      new EnumMap<TerminalEnum,Action<TerminalEnum,ProductionEnum,VersionEnum>[]>(TerminalEnum.class);
      
    tableMap.put(TerminalEnum.__eof__,__eof__Array);
    tableMap.put(TerminalEnum.function,functionArray);
    tableMap.put(TerminalEnum.gt,gtArray);
    tableMap.put(TerminalEnum.semicolon,semicolonArray);
    tableMap.put(TerminalEnum.lt,ltArray);
    tableMap.put(TerminalEnum._if,_ifArray);
    tableMap.put(TerminalEnum.dot,dotArray);
    tableMap.put(TerminalEnum.ge,geArray);
    tableMap.put(TerminalEnum.ne,neArray);
    tableMap.put(TerminalEnum._return,_returnArray);
    tableMap.put(TerminalEnum.mul,mulArray);
    tableMap.put(TerminalEnum.lcurl,lcurlArray);
    tableMap.put(TerminalEnum.le,leArray);
    tableMap.put(TerminalEnum.eq,eqArray);
    tableMap.put(TerminalEnum.var,varArray);
    tableMap.put(TerminalEnum.integer,integerArray);
    tableMap.put(TerminalEnum.add,addArray);
    tableMap.put(TerminalEnum.div,divArray);
    tableMap.put(TerminalEnum.rpar,rparArray);
    tableMap.put(TerminalEnum.id,idArray);
    tableMap.put(TerminalEnum.comma,commaArray);
    tableMap.put(TerminalEnum.rem,remArray);
    tableMap.put(TerminalEnum._else,_elseArray);
    tableMap.put(TerminalEnum.text,textArray);
    tableMap.put(TerminalEnum.colon,colonArray);
    tableMap.put(TerminalEnum.assign,assignArray);
    tableMap.put(TerminalEnum.eol,eolArray);
    tableMap.put(TerminalEnum.lpar,lparArray);
    tableMap.put(TerminalEnum.sub,subArray);
    tableMap.put(TerminalEnum.rcurl,rcurlArray);
    initBranchArrayTable();
    
    StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>[] tableMetadata = createStateMetadataTable();
    
    EnumMap<NonTerminalEnum,Integer> tableStarts =
      new EnumMap<NonTerminalEnum,Integer>(NonTerminalEnum.class);
    tableStarts.put(NonTerminalEnum.script,0);
    table = new ParserTable<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>(tableMap,branchArrayTable,tableMetadata,tableStarts,VersionEnum.values(),99,TerminalEnum.__eof__,null);
  } 

  // metadata aren't stored in local vars because it freak-out the register allocator of android
  @SuppressWarnings("unchecked")
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>[] createStateMetadataTable() {
        metadata0_return_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum._return,null);
    metadata0lt_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.lt,null);
    metadata0rpar_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.rpar,null);
    metadata0rem_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.rem,null);
    metadata0eol_metadata0reduceeoi__eol = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.eol,reduceeoi__eol);
    metadata0instr_star_0_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.instr_star_0,null);
    metadata0__eof___metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.__eof__,null);
    metadata0lpar_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.lpar,null);
    metadata0_else_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum._else,null);
    metadata0mul_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.mul,null);
    metadata0null_metadata0reduceinstr_star_0_empty = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(null,reduceinstr_star_0_empty);
    metadata0instr_star_1_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.instr_star_1,null);
    metadata0_if_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum._if,null);
    metadata0id_metadata0reduceid_optional_4_id = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.id,reduceid_optional_4_id);
    metadata0dot_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.dot,null);
    metadata0rpar_metadata0reduceexpr_method_call = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.rpar,reduceexpr_method_call);
    metadata0gt_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.gt,null);
    metadata0comma_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.comma,null);
    metadata0block_metadata0reduceinstr_if = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.block,reduceinstr_if);
    metadata0assign_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.assign,null);
    metadata0eol_metadata0reduceeol_optional_3_eol = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.eol,reduceeol_optional_3_eol);
    metadata0rpar_metadata0reduceexpr_parens = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.rpar,reduceexpr_parens);
    metadata0expr_star_2_metadata0reduceargs = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.expr_star_2,reduceargs);
    metadata0eoi_metadata0reduceinstr_field_assign = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.eoi,reduceinstr_field_assign);
    metadata0init_metadata0reduceinit_star_6_rec = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.init,reduceinit_star_6_rec);
    metadata0eoi_metadata0reduceinstr_var_decl = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.eoi,reduceinstr_var_decl);
    metadata0ne_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.ne,null);
    metadata0id_metadata0reduceid_star_5_element = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.id,reduceid_star_5_element);
    metadata0eol_optional_3_metadata0reduceinit = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.eol_optional_3,reduceinit);
    metadata0init_metadata0reduceinit_star_6_element = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.init,reduceinit_star_6_element);
    metadata0instr_metadata0reduceinstr_star_0_rec = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.instr,reduceinstr_star_0_rec);
    metadata0ge_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.ge,null);
    metadata0init_star_6_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.init_star_6,null);
    metadata0integer_metadata0reduceexpr_numeric = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.integer,reduceexpr_numeric);
    metadata0eoi_metadata0reduceinstr_expr = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.eoi,reduceinstr_expr);
    metadata0var_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.var,null);
    metadata0sub_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.sub,null);
    metadata0colon_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.colon,null);
    metadata0eq_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.eq,null);
    metadata0id_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.id,null);
    metadata0instr_metadata0reduceinstr_star_1_rec = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.instr,reduceinstr_star_1_rec);
    metadata0init_star_6_sub_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.init_star_6_sub,null);
    metadata0rcurl_metadata0reduceblock = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.rcurl,reduceblock);
    metadata0add_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.add,null);
    metadata0eoi_metadata0reduceinstr_return = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.eoi,reduceinstr_return);
    metadata0id_optional_4_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.id_optional_4,null);
    metadata0le_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.le,null);
    metadata0function_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.function,null);
    metadata0semicolon_metadata0reduceeoi__semicolon = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.semicolon,reduceeoi__semicolon);
    metadata0args_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.args,null);
    metadata0eoi_metadata0reduceinstr_var_assign = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.eoi,reduceinstr_var_assign);
    metadata0block_metadata0reduceexpr_function_creation = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.block,reduceexpr_function_creation);
    metadata0id_metadata0reduceexpr_id = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.id,reduceexpr_id);
    metadata0expr_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.expr,null);
    metadata0div_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.div,null);
    metadata0expr_star_2_sub_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.expr_star_2_sub,null);
    metadata0script_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.script,null);
    metadata0text_metadata0reduceexpr_text = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.text,reduceexpr_text);
    metadata0block_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.block,null);
    metadata0lcurl_metadata0reduceinstr_star_1_empty = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.lcurl,reduceinstr_star_1_empty);
    metadata0id_metadata0reduceid_star_5_rec = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.id,reduceid_star_5_rec);
    metadata0id_star_5_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.id_star_5,null);
    metadata0lcurl_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.lcurl,null);
    metadata0rpar_metadata0reduceexpr_apply = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.rpar,reduceexpr_apply);
    metadata0rcurl_metadata0reduceexpr_new = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithTerminal(TerminalEnum.rcurl,reduceexpr_new);
    metadata0id_star_5_sub_metadata0null = StateMetadata.<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>createAllVersionWithNonTerminal(NonTerminalEnum.id_star_5_sub,null);

    return (StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum>[])new StateMetadata<?,?,?,?>[]{metadata0null_metadata0reduceinstr_star_0_empty,metadata0instr_star_0_metadata0null,metadata0_return_metadata0null,metadata0function_metadata0null,metadata0id_metadata0reduceid_optional_4_id,metadata0id_optional_4_metadata0null,metadata0lpar_metadata0null,metadata0id_metadata0reduceid_star_5_element,metadata0id_star_5_metadata0null,metadata0rpar_metadata0null,metadata0lcurl_metadata0reduceinstr_star_1_empty,metadata0instr_star_1_metadata0null,metadata0id_metadata0null,metadata0assign_metadata0null,metadata0id_metadata0reduceexpr_id,metadata0lpar_metadata0null,metadata0lcurl_metadata0null,metadata0id_metadata0null,metadata0colon_metadata0null,metadata0integer_metadata0reduceexpr_numeric,metadata0text_metadata0reduceexpr_text,metadata0expr_metadata0null,metadata0gt_metadata0null,metadata0expr_metadata0null,metadata0lt_metadata0null,metadata0expr_metadata0null,metadata0rem_metadata0null,metadata0expr_metadata0null,metadata0dot_metadata0null,metadata0id_metadata0null,metadata0lpar_metadata0null,metadata0args_metadata0null,metadata0rpar_metadata0reduceexpr_method_call,metadata0expr_star_2_metadata0reduceargs,metadata0expr_metadata0null,metadata0ge_metadata0null,metadata0expr_metadata0null,metadata0ne_metadata0null,metadata0expr_metadata0null,metadata0mul_metadata0null,metadata0expr_metadata0null,metadata0lpar_metadata0null,metadata0args_metadata0null,metadata0rpar_metadata0reduceexpr_apply,metadata0expr_star_2_sub_metadata0null,metadata0comma_metadata0null,metadata0expr_metadata0null,metadata0le_metadata0null,metadata0expr_metadata0null,metadata0eq_metadata0null,metadata0expr_metadata0null,metadata0sub_metadata0null,metadata0expr_metadata0null,metadata0add_metadata0null,metadata0expr_metadata0null,metadata0div_metadata0null,metadata0expr_metadata0null,metadata0eol_metadata0reduceeol_optional_3_eol,metadata0eol_optional_3_metadata0reduceinit,metadata0init_metadata0reduceinit_star_6_element,metadata0init_star_6_sub_metadata0null,metadata0comma_metadata0null,metadata0init_metadata0reduceinit_star_6_rec,metadata0init_star_6_metadata0null,metadata0rcurl_metadata0reduceexpr_new,metadata0expr_metadata0null,metadata0rpar_metadata0reduceexpr_parens,metadata0expr_metadata0null,metadata0semicolon_metadata0reduceeoi__semicolon,metadata0eol_metadata0reduceeoi__eol,metadata0eoi_metadata0reduceinstr_var_assign,metadata0_if_metadata0null,metadata0expr_metadata0null,metadata0block_metadata0null,metadata0_else_metadata0null,metadata0block_metadata0reduceinstr_if,metadata0var_metadata0null,metadata0id_metadata0null,metadata0assign_metadata0null,metadata0expr_metadata0null,metadata0eoi_metadata0reduceinstr_var_decl,metadata0rcurl_metadata0reduceblock,metadata0instr_metadata0reduceinstr_star_1_rec,metadata0expr_metadata0null,metadata0dot_metadata0null,metadata0id_metadata0null,metadata0assign_metadata0null,metadata0expr_metadata0null,metadata0eoi_metadata0reduceinstr_field_assign,metadata0eoi_metadata0reduceinstr_expr,metadata0block_metadata0reduceexpr_function_creation,metadata0id_star_5_sub_metadata0null,metadata0comma_metadata0null,metadata0id_metadata0reduceid_star_5_rec,metadata0expr_metadata0null,metadata0eoi_metadata0reduceinstr_return,metadata0instr_metadata0reduceinstr_star_0_rec,metadata0script_metadata0null,metadata0__eof___metadata0null};
  }

  
  private int[] eol_optional_3Gotoes;

  private void initeol_optional_3Gotoes() {
    eol_optional_3Gotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] instrGotoes;

  private void initinstrGotoes() {
    instrGotoes = 
      new int[]{-1,96,-1,-1,-1,-1,-1,-1,-1,-1,-1,82,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] init_star_6_subGotoes;

  private void initinit_star_6_subGotoes() {
    init_star_6_subGotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,60,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] scriptGotoes;

  private void initscriptGotoes() {
    scriptGotoes = 
      new int[]{97,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] init_star_6Gotoes;

  private void initinit_star_6Gotoes() {
    init_star_6Gotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,63,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] expr_star_2Gotoes;

  private void initexpr_star_2Gotoes() {
    expr_star_2Gotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] id_star_5Gotoes;

  private void initid_star_5Gotoes() {
    id_star_5Gotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,8,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] id_star_5_subGotoes;

  private void initid_star_5_subGotoes() {
    id_star_5_subGotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,91,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] expr_star_2_subGotoes;

  private void initexpr_star_2_subGotoes() {
    expr_star_2_subGotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,44,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,44,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] blockGotoes;

  private void initblockGotoes() {
    blockGotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,90,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,73,-1,75,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] initGotoes;

  private void initinitGotoes() {
    initGotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,59,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] instr_star_0Gotoes;

  private void initinstr_star_0Gotoes() {
    instr_star_0Gotoes = 
      new int[]{1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] instr_star_1Gotoes;

  private void initinstr_star_1Gotoes() {
    instr_star_1Gotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,11,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] exprGotoes;

  private void initexprGotoes() {
    exprGotoes = 
      new int[]{-1,83,94,-1,-1,-1,-1,-1,-1,-1,-1,83,-1,67,-1,65,-1,-1,21,-1,-1,-1,23,-1,25,-1,27,-1,-1,-1,34,-1,-1,-1,-1,36,-1,38,-1,40,-1,34,-1,-1,-1,46,-1,48,-1,50,-1,52,-1,54,-1,56,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,72,-1,-1,-1,-1,-1,-1,79,-1,-1,-1,-1,-1,-1,-1,87,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] argsGotoes;

  private void initargsGotoes() {
    argsGotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,31,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,42,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] id_optional_4Gotoes;

  private void initid_optional_4Gotoes() {
    id_optional_4Gotoes = 
      new int[]{-1,-1,-1,5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
  }
  
  private int[] eoiGotoes;

  private void initeoiGotoes() {
    eoiGotoes = 
      new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,70,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,80,-1,-1,-1,89,-1,-1,-1,88,-1,-1,-1,-1,-1,-1,95,-1,-1,-1,-1};
  }

  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] __eof__Array;
  @SuppressWarnings("unchecked")
  private void init__eof__Array() {
    __eof__Array=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,reducescript,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,branch0,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,branch0,branch0,reduceinstr_var_decl,reduceblock,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,accept,accept};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] functionArray;
  @SuppressWarnings("unchecked")
  private void initfunctionArray() {
    functionArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift3,shift3,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift3,branch0,shift3,branch0,shift3,branch0,branch0,shift3,branch0,branch0,branch0,shift3,branch0,shift3,branch0,shift3,branch0,branch0,branch0,shift3,branch0,branch0,branch0,branch0,shift3,branch0,shift3,branch0,shift3,branch0,shift3,branch0,branch0,branch0,shift3,branch0,shift3,branch0,shift3,branch0,shift3,branch0,shift3,branch0,shift3,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,shift3,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,shift3,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,shift3,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] gtArray;
  @SuppressWarnings("unchecked")
  private void initgtArray() {
    gtArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift22,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift22,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift22,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift22,reduceexpr_parens,shift22,branch0,branch0,branch0,branch0,shift22,branch0,branch0,branch0,branch0,branch0,branch0,shift22,branch0,reduceblock,branch0,shift22,branch0,reduceexpr_field_access,branch0,shift22,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift22,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] semicolonArray;
  @SuppressWarnings("unchecked")
  private void initsemicolonArray() {
    semicolonArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,branch0,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,branch0,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,branch0,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,branch0,reduceexpr_parens,shift68,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,shift68,branch0,reduceblock,branch0,shift68,branch0,reduceexpr_field_access,branch0,shift68,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift68,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] ltArray;
  @SuppressWarnings("unchecked")
  private void initltArray() {
    ltArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift24,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift24,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift24,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift24,reduceexpr_parens,shift24,branch0,branch0,branch0,branch0,shift24,branch0,branch0,branch0,branch0,branch0,branch0,shift24,branch0,reduceblock,branch0,shift24,branch0,reduceexpr_field_access,branch0,shift24,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift24,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] _ifArray;
  @SuppressWarnings("unchecked")
  private void init_ifArray() {
    _ifArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift71,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift71,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,branch0,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,branch0,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,branch0,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] dotArray;
  @SuppressWarnings("unchecked")
  private void initdotArray() {
    dotArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift28,branch0,shift28,branch0,shift28,branch0,shift28,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift28,branch0,shift28,branch0,shift28,branch0,shift28,branch0,branch0,reduceexpr_apply,branch0,branch0,shift28,branch0,shift28,branch0,shift28,branch0,shift28,branch0,shift28,branch0,shift28,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift28,reduceexpr_parens,shift28,branch0,branch0,branch0,branch0,shift28,branch0,branch0,branch0,branch0,branch0,branch0,shift28,branch0,reduceblock,branch0,shift84,branch0,reduceexpr_field_access,branch0,shift28,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift28,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] geArray;
  @SuppressWarnings("unchecked")
  private void initgeArray() {
    geArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift35,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift35,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift35,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift35,reduceexpr_parens,shift35,branch0,branch0,branch0,branch0,shift35,branch0,branch0,branch0,branch0,branch0,branch0,shift35,branch0,reduceblock,branch0,shift35,branch0,reduceexpr_field_access,branch0,shift35,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift35,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] neArray;
  @SuppressWarnings("unchecked")
  private void initneArray() {
    neArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift37,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift37,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift37,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift37,reduceexpr_parens,shift37,branch0,branch0,branch0,branch0,shift37,branch0,branch0,branch0,branch0,branch0,branch0,shift37,branch0,reduceblock,branch0,shift37,branch0,reduceexpr_field_access,branch0,shift37,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift37,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] _returnArray;
  @SuppressWarnings("unchecked")
  private void init_returnArray() {
    _returnArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift2,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift2,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,branch0,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,branch0,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,branch0,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] mulArray;
  @SuppressWarnings("unchecked")
  private void initmulArray() {
    mulArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift39,branch0,shift39,branch0,shift39,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift39,branch0,shift39,branch0,shift39,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift39,branch0,shift39,branch0,shift39,branch0,shift39,branch0,shift39,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift39,reduceexpr_parens,shift39,branch0,branch0,branch0,branch0,shift39,branch0,branch0,branch0,branch0,branch0,branch0,shift39,branch0,reduceblock,branch0,shift39,branch0,reduceexpr_field_access,branch0,shift39,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift39,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] lcurlArray;
  @SuppressWarnings("unchecked")
  private void initlcurlArray() {
    lcurlArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift16,shift16,branch0,branch0,branch0,branch0,branch0,branch0,shift10,reduceinstr_star_1_empty,shift16,branch0,shift16,reduceexpr_id,shift16,branch0,branch0,shift16,reduceexpr_numeric,reduceexpr_text,branch0,shift16,reduceexpr_gt,shift16,reduceexpr_lt,shift16,reduceexpr_rem,branch0,reduceexpr_field_access,shift16,branch0,reduceexpr_method_call,branch0,branch0,shift16,reduceexpr_ge,shift16,reduceexpr_ne,shift16,reduceexpr_mul,shift16,branch0,reduceexpr_apply,branch0,shift16,branch0,shift16,reduceexpr_le,shift16,reduceexpr_eq,shift16,reduceexpr_sub,shift16,reduceexpr_add,shift16,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,branch0,reduceexpr_parens,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,shift16,shift10,branch0,shift10,reduceinstr_if,branch0,branch0,shift16,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,shift16,branch0,reduceinstr_field_assign,reduceinstr_expr,reduceexpr_function_creation,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] leArray;
  @SuppressWarnings("unchecked")
  private void initleArray() {
    leArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift47,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift47,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift47,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift47,reduceexpr_parens,shift47,branch0,branch0,branch0,branch0,shift47,branch0,branch0,branch0,branch0,branch0,branch0,shift47,branch0,reduceblock,branch0,shift47,branch0,reduceexpr_field_access,branch0,shift47,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift47,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] eqArray;
  @SuppressWarnings("unchecked")
  private void initeqArray() {
    eqArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift49,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift49,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift49,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift49,reduceexpr_parens,shift49,branch0,branch0,branch0,branch0,shift49,branch0,branch0,branch0,branch0,branch0,branch0,shift49,branch0,reduceblock,branch0,shift49,branch0,reduceexpr_field_access,branch0,shift49,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift49,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] varArray;
  @SuppressWarnings("unchecked")
  private void initvarArray() {
    varArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift76,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift76,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,branch0,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,branch0,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,branch0,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] integerArray;
  @SuppressWarnings("unchecked")
  private void initintegerArray() {
    integerArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift19,shift19,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift19,branch0,shift19,branch0,shift19,branch0,branch0,shift19,branch0,branch0,branch0,shift19,branch0,shift19,branch0,shift19,branch0,branch0,branch0,shift19,branch0,branch0,branch0,branch0,shift19,branch0,shift19,branch0,shift19,branch0,shift19,branch0,branch0,branch0,shift19,branch0,shift19,branch0,shift19,branch0,shift19,branch0,shift19,branch0,shift19,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,shift19,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,shift19,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,shift19,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] addArray;
  @SuppressWarnings("unchecked")
  private void initaddArray() {
    addArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift53,branch0,shift53,branch0,shift53,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift53,branch0,shift53,branch0,shift53,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift53,branch0,shift53,branch0,shift53,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift53,reduceexpr_parens,shift53,branch0,branch0,branch0,branch0,shift53,branch0,branch0,branch0,branch0,branch0,branch0,shift53,branch0,reduceblock,branch0,shift53,branch0,reduceexpr_field_access,branch0,shift53,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift53,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] divArray;
  @SuppressWarnings("unchecked")
  private void initdivArray() {
    divArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift55,branch0,shift55,branch0,shift55,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift55,branch0,shift55,branch0,shift55,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift55,branch0,shift55,branch0,shift55,branch0,shift55,branch0,shift55,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift55,reduceexpr_parens,shift55,branch0,branch0,branch0,branch0,shift55,branch0,branch0,branch0,branch0,branch0,branch0,shift55,branch0,reduceblock,branch0,shift55,branch0,reduceexpr_field_access,branch0,shift55,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift55,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] rparArray;
  @SuppressWarnings("unchecked")
  private void initrparArray() {
    rparArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,reduceid_star_5_empty,reduceid_star_5_element,shift9,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,branch0,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,reduceexpr_star_2_empty,shift32,reduceexpr_method_call,reduceargs,reduceexpr_star_2_element,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,reduceexpr_star_2_empty,shift43,reduceexpr_apply,reduceexpr_star_2_through,branch0,reduceexpr_star_2_rec,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift66,reduceexpr_parens,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceblock,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_function_creation,reduceid_star_5_through,branch0,reduceid_star_5_rec,branch0,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] idArray;
  @SuppressWarnings("unchecked")
  private void initidArray() {
    idArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift12,shift14,shift4,branch0,branch0,shift7,branch0,branch0,branch0,reduceinstr_star_1_empty,shift12,branch0,shift14,branch0,shift14,shift17,branch0,shift14,branch0,branch0,branch0,shift14,branch0,shift14,branch0,shift14,branch0,shift29,branch0,shift14,branch0,branch0,branch0,branch0,shift14,branch0,shift14,branch0,shift14,branch0,shift14,branch0,branch0,branch0,shift14,branch0,shift14,branch0,shift14,branch0,shift14,branch0,shift14,branch0,shift14,branch0,branch0,branch0,branch0,branch0,shift17,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,shift14,branch0,branch0,branch0,reduceinstr_if,shift77,branch0,shift14,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,shift85,branch0,shift14,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,shift93,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] commaArray;
  @SuppressWarnings("unchecked")
  private void initcommaArray() {
    commaArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceid_star_5_element,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,reduceeol_optional_3_empty,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,reduceexpr_star_2_element,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,shift45,branch0,reduceexpr_star_2_rec,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,reduceeol_optional_3_eol,reduceinit,reduceinit_star_6_element,shift61,branch0,reduceinit_star_6_rec,branch0,reduceexpr_new,branch0,reduceexpr_parens,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceblock,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_function_creation,shift92,branch0,reduceid_star_5_rec,branch0,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] remArray;
  @SuppressWarnings("unchecked")
  private void initremArray() {
    remArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift26,branch0,shift26,branch0,shift26,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift26,branch0,shift26,branch0,shift26,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift26,branch0,shift26,branch0,shift26,branch0,shift26,branch0,shift26,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift26,reduceexpr_parens,shift26,branch0,branch0,branch0,branch0,shift26,branch0,branch0,branch0,branch0,branch0,branch0,shift26,branch0,reduceblock,branch0,shift26,branch0,reduceexpr_field_access,branch0,shift26,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift26,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] _elseArray;
  @SuppressWarnings("unchecked")
  private void init_elseArray() {
    _elseArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,shift74,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceblock,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] textArray;
  @SuppressWarnings("unchecked")
  private void inittextArray() {
    textArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift20,shift20,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift20,branch0,shift20,branch0,shift20,branch0,branch0,shift20,branch0,branch0,branch0,shift20,branch0,shift20,branch0,shift20,branch0,branch0,branch0,shift20,branch0,branch0,branch0,branch0,shift20,branch0,shift20,branch0,shift20,branch0,shift20,branch0,branch0,branch0,shift20,branch0,shift20,branch0,shift20,branch0,shift20,branch0,shift20,branch0,shift20,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,shift20,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,shift20,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,shift20,branch0,reduceinstr_field_assign,reduceinstr_expr,branch0,branch0,branch0,branch0,branch0,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] colonArray;
  @SuppressWarnings("unchecked")
  private void initcolonArray() {
    colonArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,shift18,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] assignArray;
  @SuppressWarnings("unchecked")
  private void initassignArray() {
    assignArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,shift13,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,shift78,branch0,branch0,branch0,branch0,branch0,branch0,branch0,shift86,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] eolArray;
  @SuppressWarnings("unchecked")
  private void initeolArray() {
    eolArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift57,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,branch0,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,branch0,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,branch0,reduceexpr_parens,shift69,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,shift69,branch0,reduceblock,branch0,shift69,branch0,reduceexpr_field_access,branch0,shift69,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift69,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] lparArray;
  @SuppressWarnings("unchecked")
  private void initlparArray() {
    lparArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,shift15,shift15,reduceid_optional_4_empty,reduceid_optional_4_id,shift6,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift15,reduceexpr_id,shift15,reduceexpr_id,shift15,branch0,branch0,shift15,reduceexpr_numeric,reduceexpr_text,shift41,shift15,shift41,shift15,shift41,shift15,shift41,branch0,shift30,shift15,branch0,reduceexpr_method_call,branch0,shift41,shift15,shift41,shift15,shift41,shift15,shift41,shift15,branch0,reduceexpr_apply,branch0,shift15,shift41,shift15,shift41,shift15,shift41,shift15,shift41,shift15,shift41,shift15,shift41,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift41,reduceexpr_parens,shift41,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,shift15,shift41,branch0,branch0,reduceinstr_if,branch0,branch0,shift15,shift41,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,shift41,branch0,shift30,shift15,shift41,reduceinstr_field_assign,reduceinstr_expr,reduceexpr_function_creation,branch0,branch0,branch0,shift41,reduceinstr_return,reduceinstr_star_0_rec,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] subArray;
  @SuppressWarnings("unchecked")
  private void initsubArray() {
    subArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_id,branch0,reduceexpr_id,branch0,branch0,branch0,branch0,reduceexpr_numeric,reduceexpr_text,shift51,branch0,shift51,branch0,shift51,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,shift51,branch0,shift51,branch0,shift51,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,shift51,branch0,shift51,branch0,shift51,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceexpr_new,shift51,reduceexpr_parens,shift51,branch0,branch0,branch0,branch0,shift51,branch0,branch0,branch0,branch0,branch0,branch0,shift51,branch0,reduceblock,branch0,shift51,branch0,reduceexpr_field_access,branch0,shift51,branch0,branch0,reduceexpr_function_creation,branch0,branch0,branch0,shift51,branch0,branch0,branch0,branch0};
  }
  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] rcurlArray;
  @SuppressWarnings("unchecked")
  private void initrcurlArray() {
    rcurlArray=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,branch0,reduceinstr_star_1_empty,shift81,branch0,branch0,reduceexpr_id,branch0,reduceinit_star_6_empty,branch0,branch0,reduceexpr_numeric,reduceexpr_text,reduceeol_optional_3_empty,branch0,reduceexpr_gt,branch0,reduceexpr_lt,branch0,reduceexpr_rem,branch0,reduceexpr_field_access,branch0,branch0,reduceexpr_method_call,branch0,branch0,branch0,reduceexpr_ge,branch0,reduceexpr_ne,branch0,reduceexpr_mul,branch0,branch0,reduceexpr_apply,branch0,branch0,branch0,branch0,reduceexpr_le,branch0,reduceexpr_eq,branch0,reduceexpr_sub,branch0,reduceexpr_add,branch0,reduceexpr_div,reduceeol_optional_3_eol,reduceinit,reduceinit_star_6_element,reduceinit_star_6_through,branch0,reduceinit_star_6_rec,shift64,reduceexpr_new,branch0,reduceexpr_parens,branch0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,branch0,branch0,branch0,branch0,reduceinstr_if,branch0,branch0,branch0,branch0,reduceinstr_var_decl,reduceblock,reduceinstr_star_1_rec,branch0,branch0,branch0,branch0,branch0,reduceinstr_field_assign,reduceinstr_expr,reduceexpr_function_creation,branch0,branch0,branch0,branch0,reduceinstr_return,branch0,branch0,branch0};
  }

  private Action<TerminalEnum,ProductionEnum,VersionEnum>[] branchArrayTable;
  @SuppressWarnings("unchecked")
  private void initBranchArrayTable() {
    branchArrayTable=(Action<TerminalEnum,ProductionEnum,VersionEnum>[])new Action<?,?,?>[]{reduceinstr_star_0_empty,reducescript,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,error0,reduceeoi__semicolon,reduceeoi__eol,reduceinstr_var_assign,error0,error0,error0,error0,reduceinstr_if,error0,error0,error0,error0,reduceinstr_var_decl,reduceblock,error0,error0,error0,error0,error0,error0,reduceinstr_field_assign,reduceinstr_expr,error0,error0,error0,error0,error0,reduceinstr_return,reduceinstr_star_0_rec,exit,exit};
  }

  private final ParserTable<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> table;
  
  public static final ParserTable<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> createTable() {
    return new ParserDataTable().table;
  }

  private final AcceptAction<TerminalEnum,ProductionEnum,VersionEnum> accept;
  private final ExitAction<TerminalEnum,ProductionEnum,VersionEnum> exit;

  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceid_optional_4_id;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_parens;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_ge;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceid_star_5_rec;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_add;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_star_1_empty;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_star_2_element;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_new;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_mul;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinit;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_field_assign;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_lt;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinit_star_6_through;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceeol_optional_3_empty;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_field_access;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_star_2_through;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceid_optional_4_empty;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_id;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinit_star_6_element;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceblock;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_gt;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_rem;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceargs;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_le;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_method_call;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinit_star_6_empty;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceeoi__semicolon;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_ne;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceeoi__eol;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_if;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_numeric;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_star_1_rec;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceid_star_5_through;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_div;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceeol_optional_3_eol;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_apply;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_star_2_empty;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_function_creation;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_eq;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_sub;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_star_0_empty;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinit_star_6_rec;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_var_decl;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_star_0_rec;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_return;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceid_star_5_element;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_expr;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reducescript;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_star_2_rec;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceinstr_var_assign;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceexpr_text;
  private final ReduceAction<TerminalEnum,ProductionEnum,VersionEnum> reduceid_star_5_empty;

  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift81;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift14;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift61;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift85;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift84;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift45;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift41;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift37;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift39;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift92;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift47;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift12;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift26;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift68;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift49;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift24;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift55;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift78;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift29;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift22;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift74;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift6;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift17;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift53;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift20;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift18;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift66;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift3;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift7;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift2;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift51;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift35;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift43;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift32;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift64;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift13;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift69;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift30;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift16;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift19;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift15;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift93;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift4;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift28;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift10;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift9;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift77;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift71;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift57;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift86;
  private final ShiftAction<TerminalEnum,ProductionEnum,VersionEnum> shift76;


  private final ErrorAction<TerminalEnum,ProductionEnum,VersionEnum> error0;

  private final BranchAction<TerminalEnum,ProductionEnum,VersionEnum> branch0;


  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0_return_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0lt_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0rpar_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0rem_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eol_metadata0reduceeoi__eol;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0instr_star_0_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0__eof___metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0lpar_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0_else_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0mul_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0null_metadata0reduceinstr_star_0_empty;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0instr_star_1_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0_if_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_metadata0reduceid_optional_4_id;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0dot_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0rpar_metadata0reduceexpr_method_call;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0gt_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0comma_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0block_metadata0reduceinstr_if;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0assign_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eol_metadata0reduceeol_optional_3_eol;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0rpar_metadata0reduceexpr_parens;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0expr_star_2_metadata0reduceargs;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eoi_metadata0reduceinstr_field_assign;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0init_metadata0reduceinit_star_6_rec;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eoi_metadata0reduceinstr_var_decl;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0ne_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_metadata0reduceid_star_5_element;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eol_optional_3_metadata0reduceinit;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0init_metadata0reduceinit_star_6_element;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0instr_metadata0reduceinstr_star_0_rec;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0ge_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0init_star_6_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0integer_metadata0reduceexpr_numeric;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eoi_metadata0reduceinstr_expr;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0var_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0sub_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0colon_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eq_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0instr_metadata0reduceinstr_star_1_rec;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0init_star_6_sub_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0rcurl_metadata0reduceblock;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0add_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eoi_metadata0reduceinstr_return;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_optional_4_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0le_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0function_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0semicolon_metadata0reduceeoi__semicolon;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0args_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0eoi_metadata0reduceinstr_var_assign;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0block_metadata0reduceexpr_function_creation;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_metadata0reduceexpr_id;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0expr_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0div_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0expr_star_2_sub_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0script_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0text_metadata0reduceexpr_text;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0block_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0lcurl_metadata0reduceinstr_star_1_empty;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_metadata0reduceid_star_5_rec;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_star_5_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0lcurl_metadata0null;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0rpar_metadata0reduceexpr_apply;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0rcurl_metadata0reduceexpr_new;
  private StateMetadata<TerminalEnum,NonTerminalEnum,ProductionEnum,VersionEnum> metadata0id_star_5_sub_metadata0null;
}
