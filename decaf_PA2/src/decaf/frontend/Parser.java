//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short REPEAT=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short NEW=272;
public final static short UNTIL=273;
public final static short PRINT=274;
public final static short READ_INTEGER=275;
public final static short READ_LINE=276;
public final static short LITERAL=277;
public final static short IDENTIFIER=278;
public final static short AND=279;
public final static short OR=280;
public final static short STATIC=281;
public final static short INSTANCEOF=282;
public final static short LESS_EQUAL=283;
public final static short GREATER_EQUAL=284;
public final static short EQUAL=285;
public final static short NOT_EQUAL=286;
public final static short SELF_INC=287;
public final static short SELF_DEC=288;
public final static short SWITCH=289;
public final static short CASE=290;
public final static short DEFAULT=291;
public final static short UMINUS=292;
public final static short EMPTY=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   13,   16,   12,   12,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   18,   18,   18,   32,   32,   29,   29,   31,   30,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   30,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   30,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   30,
   34,   34,   33,   33,   35,   35,   20,   21,   22,   23,
   14,   14,   15,   15,   36,   37,   24,   24,   25,   25,
   28,   19,   38,   38,   26,   26,   27,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    4,    1,    2,    0,    1,    2,
    1,    1,    1,    1,    1,    2,    2,    2,    2,    2,
    1,    3,    1,    0,    2,    0,    2,    4,    5,    1,
    1,    1,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    2,    2,    2,
    2,    2,    5,    3,    3,    1,    4,    5,    6,    5,
    1,    1,    1,    0,    3,    1,    5,    9,    6,    5,
    2,    0,    1,    0,    4,    3,    2,    2,    2,    2,
    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   28,   23,   20,   22,    0,   82,   76,    0,    0,    0,
    0,    0,  101,    0,    0,    0,    0,   81,    0,    0,
    0,    0,   24,    0,    0,    0,   29,   41,   27,    0,
   31,   32,   33,   34,   35,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   52,    0,    0,    0,    0,    0,
    0,   50,    0,   51,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   30,   36,   37,
   38,   39,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   70,   72,    0,    0,    0,    0,   74,   75,    0,    0,
   66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   77,    0,    0,  107,    0,    0,    0,
   48,    0,    0,    0,   87,    0,    0,    0,   78,    0,
    0,   80,   92,   90,    0,   49,    0,   89,    0,  102,
   79,    0,    0,  103,    0,    0,    0,   91,   93,    0,
    0,   28,   25,   88,   28,    0,   96,   95,
};
final static short yydgoto[] = {                          2,
    3,    4,   67,   20,   33,    8,   11,   22,   34,   35,
   68,  216,  194,  202,  207,  217,   69,   70,   71,   72,
   73,   74,   75,   76,   77,   78,   79,   80,   92,   82,
   94,   84,  183,   85,  146,  208,  209,  200,
};
final static short yysindex[] = {                      -246,
 -255,    0, -246,    0, -225,    0, -235,  -71,    0,    0,
  140,    0,    0,    0,    0, -217,  -52,    0,    0,    4,
  -83,    0,    0,  -82,    0,   13,  -26,   29,  -52,    0,
  -52,    0,  -74,   31,   27,   32,    0,  -49,  -52,  -49,
    0,    0,    0,    0,    2,    0,    0,   38,   42,   35,
   43,   84,    0, -138,   44,   46,   48,    0,   51,   84,
   84,   54,    0,   84,   84,   53,    0,    0,    0,   36,
    0,    0,    0,    0,    0,   39,   45,   47,   50,   55,
   40,  592,    0, -181,    0,   84,   84, -171,   84,   84,
   84,    0,  604,    0,   65,   16,   84,   69,   71,   84,
  -41,  -41, -165,  139,  -41,  -41,   84,    0,    0,    0,
    0,    0,    0,   84,   84,   84,   84,   84,   84,   84,
   84,   84,   84,   84,   84,   84,   84,    0,   84,    0,
    0,   84,   76,  391,   67,  604,   90,  402,  -41,  -41,
    0,    0,   93,   63,  604,  -20,    0,    0,  413,   94,
    0,  443,  604,  863,  856,   -6,   -6,  884,  884,  -36,
  -36,  -41,  -41,  -41,   -6,   -6,  454,  465,   84,   35,
   84,   84,   35,    0,  495,   84,    0, -147,   84,   28,
    0,   84,   95,   97,    0,  506,  530, -130,    0,  604,
  109,    0,    0,    0,  604,    0,   84,    0,   35,    0,
    0, -258,  112,    0, -243,   96,   41,    0,    0,   35,
   99,    0,    0,    0,    0,   35,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  168,    0,   49,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  118,    0,    0,  138,    0,
  138,    0,    0,    0,  142,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -57,    0,    0,    0,    0,  -57,
    0,  -56,    0,    0,    0,    0,    0,    0,    0,  -85,
  -85,  -85,    0,  -85,  -85,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  759,    0,  128,    0,    0,  -85,  -57,    0,  -85,  -85,
  -85,    0,  141,    0,    0,    0,  -85,    0,    0,  -85,
  898,  922,    0,    0, 1150, 1212,  -85,    0,    0,    0,
    0,    0,    0,  -85,  -85,  -85,  -85,  -85,  -85,  -85,
  -85,  -85,  -85,  -85,  -85,  -85,  -85,    0,  -85,  557,
  568,  -85,  101,    0,    0,    0,    0,    0,  958,  967,
    0,    0,    0,  -85,  -16,    0,    0,    0,    0,    0,
    0,    0,  -37,    7,   18, 1111, 1220,   74,  714, 1043,
 1173, 1011, 1020, 1052, 1257, 1289,    0,    0,  -28,  -57,
  -85,  -85,  -57,    0,    0,  -85,    0,    0,  -85,    0,
    0,  -85,    0,  156,    0,    0,    0,  -33,    0,    5,
    0,    0,    0,    0,  -14,    0,  -25,    0,  -57,    0,
    0,   73,    0,    0,    0,    0,    0,    0,    0,  -57,
    0,    0,    0,    0,    0,   30,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  200,  199,   25,    3,    0,    0,    0,  180,    0,
   20,  171,    0,    0,    0,   -2,  -21,  -69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  780, 1463,
 1176,    0,    0,    9,   79,    0,    0,    0,
};
final static int YYTABLESIZE=1660;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        104,
  125,   44,  106,   42,  128,  123,  104,   27,   27,  128,
  124,  104,   84,   21,    1,   44,   27,  135,   46,   24,
  177,   42,    5,  176,   86,  104,   73,   86,   88,   73,
  125,  205,  206,   58,   61,  123,  121,    7,  122,  128,
  124,   62,    9,   73,   73,   85,   60,   64,   85,  129,
   64,   10,   29,   32,  129,   32,   96,   42,   65,   44,
   23,   65,   25,   43,   64,   64,   30,   61,   31,   64,
   39,   38,   40,   41,   62,   65,   65,   86,   73,   60,
   65,   87,   89,   97,  129,   98,   61,   99,   44,  104,
  100,  104,  107,   62,  108,   61,  133,  109,   60,   64,
  114,  137,   62,  110,  143,  111,  144,   60,  112,  147,
   65,  148,  150,  113,   58,  169,   61,   58,   12,   13,
   14,   15,   16,   62,   41,  171,   63,  203,   60,  172,
  191,   58,   58,  174,  179,  196,   58,   47,  199,   95,
  176,   47,   47,   47,   47,   47,   47,   47,  185,  201,
  193,  188,  210,  212,   26,   30,  215,   41,   47,   47,
   47,   47,   47,   47,   51,  213,   58,    1,   43,   51,
   51,   14,   51,   51,   51,  125,    5,  204,   19,  151,
  123,  121,   18,  122,  128,  124,   43,   51,  214,   51,
   51,   47,   46,   47,   26,   28,   83,   94,  127,  105,
  126,  132,    6,   37,   12,   13,   14,   15,   16,   19,
   36,   45,  218,  211,    0,    0,    0,    0,   51,    0,
   46,   46,    0,  104,  104,  104,  104,  104,  104,  129,
  104,  104,  104,  104,  104,    0,  104,  104,  104,  104,
  104,  104,  104,  104,  104,  141,  142,  184,  104,   46,
  141,  142,   46,  104,  104,  104,  104,  104,   12,   13,
   14,   15,   16,   46,   18,   47,   48,   49,   50,   51,
    0,   52,   53,   54,    0,   55,   56,   57,   58,    0,
  141,  142,    0,   59,    0,   64,   64,    0,   64,   65,
   66,   12,   13,   14,   15,   16,   46,   65,   47,   48,
   49,   50,   51,    0,   52,   53,   54,   46,   55,   56,
   57,   58,    0,    0,  103,   46,   59,   47,    0,   26,
   26,   64,   65,   66,   46,   54,   47,    0,   56,   57,
   58,    0,    0,    0,   54,   59,    0,   56,   57,   58,
   90,   91,    0,    0,   59,   46,    0,   47,    0,   90,
   91,    0,   58,   58,    0,   54,    0,    0,   56,   57,
   58,    0,    0,    0,    0,   59,    0,    0,    0,    0,
   90,   91,    0,    0,    0,    0,    0,    0,    0,   47,
   47,    0,    0,   47,   47,   47,   47,   47,   47,    0,
    0,    0,    0,    0,    0,    0,   12,   13,   14,   15,
   16,    0,    0,    0,    0,    0,   51,   51,    0,    0,
   51,   51,   51,   51,   51,   51,    0,  115,  116,    0,
   17,  117,  118,  119,  120,  141,  142,  125,    0,    0,
    0,  170,  123,  121,    0,  122,  128,  124,  125,    0,
    0,    0,  173,  123,  121,    0,  122,  128,  124,  125,
  127,    0,  126,  132,  123,  121,  178,  122,  128,  124,
    0,  127,    0,  126,  132,    0,    0,    0,    0,    0,
    0,    0,  127,    0,  126,  132,    0,    0,    0,  125,
    0,  129,    0,  180,  123,  121,    0,  122,  128,  124,
  125,    0,  129,    0,    0,  123,  121,    0,  122,  128,
  124,  125,  127,  129,  126,  132,  123,  121,    0,  122,
  128,  124,    0,  127,    0,  126,  132,    0,    0,    0,
    0,    0,  182,    0,  127,    0,  126,  132,    0,    0,
    0,  125,    0,  129,    0,    0,  123,  121,    0,  122,
  128,  124,  125,    0,  129,    0,  181,  123,  121,    0,
  122,  128,  124,    0,  127,  129,  126,  132,    0,    0,
    0,    0,    0,    0,  197,  127,  125,  126,  132,    0,
  198,  123,  121,    0,  122,  128,  124,    0,    0,    0,
    0,    0,    0,    0,    0,  129,    0,  189,    0,  127,
    0,  126,  132,   70,    0,    0,  129,    0,   70,   70,
    0,   70,   70,   70,   72,    0,    0,    0,    0,   72,
   72,    0,   72,   72,   72,   98,   70,    0,   70,   70,
  129,    0,    0,    0,    0,    0,  100,   72,  125,   72,
   72,    0,    0,  123,  121,    0,  122,  128,  124,    0,
  125,    0,    0,    0,    0,  123,  121,   70,  122,  128,
  124,  127,    0,  126,  132,    0,    0,    0,   72,    0,
    0,    0,    0,  127,    0,  126,  132,    0,    0,  115,
  116,    0,    0,  117,  118,  119,  120,  141,  142,    0,
  115,  116,  129,    0,  117,  118,  119,  120,  141,  142,
    0,  115,  116,    0,  129,  117,  118,  119,  120,  141,
  142,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  115,  116,    0,    0,  117,  118,  119,  120,  141,
  142,    0,  115,  116,    0,    0,  117,  118,  119,  120,
  141,  142,    0,  115,  116,    0,    0,  117,  118,  119,
  120,  141,  142,    0,   59,    0,    0,   59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   59,   59,  115,  116,    0,   59,  117,  118,  119,
  120,  141,  142,    0,  115,  116,    0,    0,  117,  118,
  119,  120,  141,  142,    0,   50,    0,    0,    0,    0,
   50,   50,    0,   50,   50,   50,   59,    0,  115,  116,
    0,    0,  117,  118,  119,  120,  141,  142,   50,    0,
   50,   50,    0,    0,   81,    0,    0,    0,    0,   81,
    0,    0,    0,    0,    0,   70,   70,    0,    0,   70,
   70,   70,   70,   70,   70,    0,   72,   72,    0,   50,
   72,   72,   72,   72,   72,   72,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   81,    0,    0,    0,
  115,  116,    0,    0,  117,  118,  119,  120,  130,  131,
    0,    0,  115,  116,    0,    0,  117,  118,  119,  120,
  141,  142,  125,    0,    0,    0,    0,  123,  121,  125,
  122,  128,  124,    0,  123,  121,    0,  122,  128,  124,
    0,    0,    0,    0,    0,  127,    0,  126,    0,    0,
  125,    0,  127,    0,  126,  123,  121,    0,  122,  128,
  124,    0,    0,    0,   67,    0,    0,    0,   67,   67,
   67,   67,   67,  127,   67,  126,  129,    0,    0,   81,
    0,    0,   81,  129,    0,   67,   67,   67,   68,   67,
   67,    0,   68,   68,   68,   68,   68,    0,   68,    0,
    0,    0,    0,    0,  129,    0,   81,    0,   81,   68,
   68,   68,    0,   68,   68,    0,    0,    0,    0,   81,
   67,    0,   59,   59,   69,   81,    0,    0,   69,   69,
   69,   69,   69,   71,   69,    0,    0,   71,   71,   71,
   71,   71,    0,   71,   68,   69,   69,   69,    0,   69,
   69,    0,    0,    0,   71,   71,   71,    0,   71,   71,
    0,    0,    0,    0,    0,    0,    0,   50,   50,    0,
    0,   50,   50,   50,   50,   50,   50,   55,    0,    0,
   69,   55,   55,   55,   55,   55,   56,   55,    0,   71,
   56,   56,   56,   56,   56,    0,   56,    0,   55,   55,
   55,    0,   55,   55,    0,    0,    0,   56,   56,   56,
    0,   56,   56,   53,    0,   53,   53,   53,   57,    0,
    0,    0,   57,   57,   57,   57,   57,    0,   57,    0,
   53,   53,   53,   55,   53,   53,    0,    0,    0,   57,
   57,   57,   56,   57,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  115,   53,    0,    0,  117,  118,
  119,  120,  141,  142,   57,  117,  118,  119,  120,  141,
  142,   62,    0,    0,   62,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  117,  118,   62,   62,
  141,  142,    0,   62,    0,    0,   67,   67,    0,    0,
   67,   67,   67,   67,    0,    0,   69,    0,    0,    0,
    0,   69,   69,    0,   69,    0,   69,    0,    0,    0,
   68,   68,    0,   62,   68,   68,   68,   68,   97,   69,
    0,   69,   69,   54,    0,   54,   54,   54,    0,    0,
   83,    0,    0,    0,    0,   83,    0,    0,    0,    0,
   54,   54,   54,    0,   54,   54,   69,   69,    0,    0,
   69,   69,   69,   69,    0,   71,   71,    0,   71,   71,
   71,   71,   71,   71,   71,    0,   71,    0,   71,    0,
   63,    0,   83,   63,    0,   54,    0,    0,    0,    0,
   99,   71,    0,   71,   71,    0,    0,   63,   63,    0,
    0,    0,   63,    0,    0,    0,    0,    0,    0,   55,
   55,    0,    0,   55,   55,   55,   55,   61,   56,   56,
   61,    0,   56,   56,   56,   56,    0,    0,    0,    0,
    0,    0,   63,    0,   61,   61,    0,    0,    0,   61,
    0,   53,   53,    0,    0,   53,   53,   53,   53,   60,
   57,   57,   60,    0,   57,   57,   57,   57,    0,    0,
    0,    0,    0,    0,    0,   83,   60,   60,   83,   61,
    0,   60,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   83,    0,   83,    0,    0,    0,    0,    0,
    0,   60,    0,    0,    0,   83,    0,    0,    0,   62,
   62,   83,    0,    0,    0,   62,   62,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   69,   69,
    0,    0,   69,   69,   69,   69,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   54,   54,    0,    0,   54,   54,   54,   54,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   71,   71,    0,    0,   71,   71,   71,   71,   63,   63,
    0,    0,    0,    0,   63,   63,    0,    0,    0,    0,
    0,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,  101,  102,  104,    0,  105,  106,    0,    0,
    0,    0,    0,    0,    0,   61,   61,    0,    0,    0,
    0,   61,   61,    0,    0,    0,    0,    0,  134,  136,
    0,  138,  139,  140,    0,    0,    0,    0,    0,  145,
    0,    0,  149,    0,    0,    0,    0,   60,   60,  152,
    0,    0,    0,   60,   60,    0,  153,  154,  155,  156,
  157,  158,  159,  160,  161,  162,  163,  164,  165,  166,
    0,  167,    0,    0,  168,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  175,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  145,    0,  186,  187,    0,    0,    0,  190,    0,
    0,  192,    0,    0,  195,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  136,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   37,   59,   59,   41,   46,   42,   40,   91,   91,   46,
   47,   45,   41,   11,  261,   41,   91,   87,  262,   17,
   41,   59,  278,   44,   41,   59,   41,   44,   50,   44,
   37,  290,  291,  277,   33,   42,   43,  263,   45,   46,
   47,   40,  278,   58,   59,   41,   45,   41,   44,   91,
   44,  123,   40,   29,   91,   31,   54,   38,   41,   40,
  278,   44,   59,   39,   58,   59,   93,   33,   40,   63,
   44,   41,   41,  123,   40,   58,   59,   40,   93,   45,
   63,   40,   40,   40,   91,   40,   33,   40,   59,  123,
   40,  125,   40,   40,   59,   33,  278,   59,   45,   93,
   61,  273,   40,   59,   40,   59,   91,   45,   59,   41,
   93,   41,  278,   59,   41,   40,   33,   44,  257,  258,
  259,  260,  261,   40,  123,   59,  125,  197,   45,   40,
  278,   58,   59,   41,   41,   41,   63,   37,  269,  278,
   44,   41,   42,   43,   44,   45,   46,   47,  170,   41,
  123,  173,   41,   58,  125,   93,   58,  123,   58,   59,
   60,   61,   62,   63,   37,  125,   93,    0,   41,   42,
   43,  123,   45,   46,   47,   37,   59,  199,   41,   41,
   42,   43,   41,   45,   46,   47,   59,   60,  210,   62,
   63,   91,  278,   93,  278,  278,   41,  125,   60,   59,
   62,   63,    3,  278,  257,  258,  259,  260,  261,   11,
   31,   41,  215,  205,   -1,   -1,   -1,   -1,   91,   -1,
  278,  278,   -1,  257,  258,  259,  260,  261,  262,   91,
  264,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  287,  288,  169,  282,  278,
  287,  288,  278,  287,  288,  289,  290,  291,  257,  258,
  259,  260,  261,  262,  125,  264,  265,  266,  267,  268,
   -1,  270,  271,  272,   -1,  274,  275,  276,  277,   -1,
  287,  288,   -1,  282,   -1,  279,  280,   -1,  287,  288,
  289,  257,  258,  259,  260,  261,  262,  280,  264,  265,
  266,  267,  268,   -1,  270,  271,  272,  278,  274,  275,
  276,  277,   -1,   -1,  261,  262,  282,  264,   -1,  290,
  291,  287,  288,  289,  262,  272,  264,   -1,  275,  276,
  277,   -1,   -1,   -1,  272,  282,   -1,  275,  276,  277,
  287,  288,   -1,   -1,  282,  262,   -1,  264,   -1,  287,
  288,   -1,  279,  280,   -1,  272,   -1,   -1,  275,  276,
  277,   -1,   -1,   -1,   -1,  282,   -1,   -1,   -1,   -1,
  287,  288,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  279,
  280,   -1,   -1,  283,  284,  285,  286,  287,  288,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,   -1,   -1,   -1,   -1,   -1,  279,  280,   -1,   -1,
  283,  284,  285,  286,  287,  288,   -1,  279,  280,   -1,
  281,  283,  284,  285,  286,  287,  288,   37,   -1,   -1,
   -1,   41,   42,   43,   -1,   45,   46,   47,   37,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   37,
   60,   -1,   62,   63,   42,   43,   44,   45,   46,   47,
   -1,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   62,   63,   -1,   -1,   -1,   37,
   -1,   91,   -1,   41,   42,   43,   -1,   45,   46,   47,
   37,   -1,   91,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   37,   60,   91,   62,   63,   42,   43,   -1,   45,
   46,   47,   -1,   60,   -1,   62,   63,   -1,   -1,   -1,
   -1,   -1,   58,   -1,   60,   -1,   62,   63,   -1,   -1,
   -1,   37,   -1,   91,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   37,   -1,   91,   -1,   93,   42,   43,   -1,
   45,   46,   47,   -1,   60,   91,   62,   63,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   60,   37,   62,   63,   -1,
   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,   -1,   60,
   -1,   62,   63,   37,   -1,   -1,   91,   -1,   42,   43,
   -1,   45,   46,   47,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   59,   60,   -1,   62,   63,
   91,   -1,   -1,   -1,   -1,   -1,   59,   60,   37,   62,
   63,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   37,   -1,   -1,   -1,   -1,   42,   43,   91,   45,   46,
   47,   60,   -1,   62,   63,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   60,   -1,   62,   63,   -1,   -1,  279,
  280,   -1,   -1,  283,  284,  285,  286,  287,  288,   -1,
  279,  280,   91,   -1,  283,  284,  285,  286,  287,  288,
   -1,  279,  280,   -1,   91,  283,  284,  285,  286,  287,
  288,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,  287,
  288,   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,
  287,  288,   -1,  279,  280,   -1,   -1,  283,  284,  285,
  286,  287,  288,   -1,   41,   -1,   -1,   44,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,  279,  280,   -1,   63,  283,  284,  285,
  286,  287,  288,   -1,  279,  280,   -1,   -1,  283,  284,
  285,  286,  287,  288,   -1,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   93,   -1,  279,  280,
   -1,   -1,  283,  284,  285,  286,  287,  288,   60,   -1,
   62,   63,   -1,   -1,   45,   -1,   -1,   -1,   -1,   50,
   -1,   -1,   -1,   -1,   -1,  279,  280,   -1,   -1,  283,
  284,  285,  286,  287,  288,   -1,  279,  280,   -1,   91,
  283,  284,  285,  286,  287,  288,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   87,   -1,   -1,   -1,
  279,  280,   -1,   -1,  283,  284,  285,  286,  287,  288,
   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,
  287,  288,   37,   -1,   -1,   -1,   -1,   42,   43,   37,
   45,   46,   47,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,
   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,
   43,   44,   45,   60,   47,   62,   91,   -1,   -1,  170,
   -1,   -1,  173,   91,   -1,   58,   59,   60,   37,   62,
   63,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   -1,   -1,   -1,   91,   -1,  197,   -1,  199,   58,
   59,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,  210,
   93,   -1,  279,  280,   37,  216,   -1,   -1,   41,   42,
   43,   44,   45,   37,   47,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   93,   58,   59,   60,   -1,   62,
   63,   -1,   -1,   -1,   58,   59,   60,   -1,   62,   63,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  279,  280,   -1,
   -1,  283,  284,  285,  286,  287,  288,   37,   -1,   -1,
   93,   41,   42,   43,   44,   45,   37,   47,   -1,   93,
   41,   42,   43,   44,   45,   -1,   47,   -1,   58,   59,
   60,   -1,   62,   63,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   63,   41,   -1,   43,   44,   45,   37,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   58,   59,   60,   93,   62,   63,   -1,   -1,   -1,   58,
   59,   60,   93,   62,   63,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  279,   93,   -1,   -1,  283,  284,
  285,  286,  287,  288,   93,  283,  284,  285,  286,  287,
  288,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  283,  284,   58,   59,
  287,  288,   -1,   63,   -1,   -1,  279,  280,   -1,   -1,
  283,  284,  285,  286,   -1,   -1,   37,   -1,   -1,   -1,
   -1,   42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,
  279,  280,   -1,   93,  283,  284,  285,  286,   59,   60,
   -1,   62,   63,   41,   -1,   43,   44,   45,   -1,   -1,
   45,   -1,   -1,   -1,   -1,   50,   -1,   -1,   -1,   -1,
   58,   59,   60,   -1,   62,   63,  279,  280,   -1,   -1,
  283,  284,  285,  286,   -1,  279,  280,   -1,   37,  283,
  284,  285,  286,   42,   43,   -1,   45,   -1,   47,   -1,
   41,   -1,   87,   44,   -1,   93,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,   63,   -1,   -1,   58,   59,   -1,
   -1,   -1,   63,   -1,   -1,   -1,   -1,   -1,   -1,  279,
  280,   -1,   -1,  283,  284,  285,  286,   41,  279,  280,
   44,   -1,  283,  284,  285,  286,   -1,   -1,   -1,   -1,
   -1,   -1,   93,   -1,   58,   59,   -1,   -1,   -1,   63,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   41,
  279,  280,   44,   -1,  283,  284,  285,  286,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  170,   58,   59,  173,   93,
   -1,   63,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  197,   -1,  199,   -1,   -1,   -1,   -1,   -1,
   -1,   93,   -1,   -1,   -1,  210,   -1,   -1,   -1,  279,
  280,  216,   -1,   -1,   -1,  285,  286,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  279,  280,
   -1,   -1,  283,  284,  285,  286,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  279,  280,   -1,   -1,  283,  284,  285,  286,  279,  280,
   -1,   -1,   -1,   -1,  285,  286,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   52,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   61,   62,   -1,   64,   65,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  279,  280,   -1,   -1,   -1,
   -1,  285,  286,   -1,   -1,   -1,   -1,   -1,   86,   87,
   -1,   89,   90,   91,   -1,   -1,   -1,   -1,   -1,   97,
   -1,   -1,  100,   -1,   -1,   -1,   -1,  279,  280,  107,
   -1,   -1,   -1,  285,  286,   -1,  114,  115,  116,  117,
  118,  119,  120,  121,  122,  123,  124,  125,  126,  127,
   -1,  129,   -1,   -1,  132,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  144,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  169,   -1,  171,  172,   -1,   -1,   -1,  176,   -1,
   -1,  179,   -1,   -1,  182,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  197,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","REPEAT","IF","ELSE","RETURN",
"BREAK","NEW","UNTIL","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER",
"AND","OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL",
"NOT_EQUAL","SELF_INC","SELF_DEC","SWITCH","CASE","DEFAULT","UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"SwitchBlock : '{' CaseList DefaultReceiver '}'",
"CaseBlock : StmtList",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : RepeatStmt",
"Stmt : SwitchStmt",
"Stmt : SelfIncStmt ';'",
"Stmt : SelfDecStmt ';'",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : SELF_INC Expr",
"Expr : Expr SELF_INC",
"Expr : SELF_DEC Expr",
"Expr : Expr SELF_DEC",
"Expr : Expr '?' Expr ':' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"RepeatStmt : REPEAT Stmt UNTIL '(' Expr ')'",
"SwitchStmt : SWITCH '(' Expr ')' SwitchBlock",
"CaseList : CaseList CaseStmt",
"CaseList :",
"DefaultReceiver : DefaultStmt",
"DefaultReceiver :",
"CaseStmt : CASE Constant ':' CaseBlock",
"DefaultStmt : DEFAULT ':' CaseBlock",
"SelfIncStmt : SELF_INC Expr",
"SelfIncStmt : Expr SELF_INC",
"SelfDecStmt : SELF_DEC Expr",
"SelfDecStmt : Expr SELF_DEC",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 527 "Parser.y"
    
    /**
     * 打印当前归约所用的语法规则<br>
     * 请勿修改。
     */
    public boolean onReduce(String rule) {
        if (rule.startsWith("$$"))
            return false;
        else
            rule = rule.replaceAll(" \\$\\$\\d+", "");

        if (rule.endsWith(":"))
            System.out.println(rule + " <empty>");
        else
            System.out.println(rule);
        return false;
    }
    
    public void diagnose() {
        yydebug = true;
        addReduceListener(this);
        yyparse();
    }
//#line 757 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 56 "Parser.y"
{
                        tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
                    }
break;
case 2:
//#line 62 "Parser.y"
{
                        yyval.clist.add(val_peek(0).cdef);
                    }
break;
case 3:
//#line 66 "Parser.y"
{
                        yyval.clist = new ArrayList<Tree.ClassDef>();
                        yyval.clist.add(val_peek(0).cdef);
                    }
break;
case 5:
//#line 76 "Parser.y"
{
                        yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
                    }
break;
case 6:
//#line 82 "Parser.y"
{
                        yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
                    }
break;
case 7:
//#line 86 "Parser.y"
{
                        yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                    }
break;
case 8:
//#line 90 "Parser.y"
{
                        yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                    }
break;
case 9:
//#line 94 "Parser.y"
{
                        yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                    }
break;
case 10:
//#line 98 "Parser.y"
{
                        yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                    }
break;
case 11:
//#line 102 "Parser.y"
{
                        yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                    }
break;
case 12:
//#line 108 "Parser.y"
{
                        yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
                    }
break;
case 13:
//#line 114 "Parser.y"
{
                        yyval.ident = val_peek(0).ident;
                    }
break;
case 14:
//#line 118 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 15:
//#line 124 "Parser.y"
{
                        yyval.flist.add(val_peek(0).vdef);
                    }
break;
case 16:
//#line 128 "Parser.y"
{
                        yyval.flist.add(val_peek(0).fdef);
                    }
break;
case 17:
//#line 132 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.flist = new ArrayList<Tree>();
                    }
break;
case 19:
//#line 140 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.vlist = new ArrayList<Tree.VarDef>(); 
                    }
break;
case 20:
//#line 147 "Parser.y"
{
                        yyval.vlist.add(val_peek(0).vdef);
                    }
break;
case 21:
//#line 151 "Parser.y"
{
                        yyval.vlist = new ArrayList<Tree.VarDef>();
                        yyval.vlist.add(val_peek(0).vdef);
                    }
break;
case 22:
//#line 158 "Parser.y"
{
                        yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
                    }
break;
case 23:
//#line 162 "Parser.y"
{
                        yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
                    }
break;
case 24:
//#line 168 "Parser.y"
{
                        yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
                    }
break;
case 25:
//#line 174 "Parser.y"
{
                        yyval.stmt = new SwitchBlock(val_peek(2).slist, val_peek(1).stmt, val_peek(3).loc);
                    }
break;
case 26:
//#line 180 "Parser.y"
{
                        yyval.stmt = new CaseBlock(val_peek(0).slist, val_peek(0).loc);
                    }
break;
case 27:
//#line 186 "Parser.y"
{
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 28:
//#line 190 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.slist = new ArrayList<Tree>();
                    }
break;
case 29:
//#line 197 "Parser.y"
{
                        yyval.stmt = val_peek(0).vdef;
                    }
break;
case 30:
//#line 202 "Parser.y"
{
                        if (yyval.stmt == null) {
                            yyval.stmt = new Tree.Skip(val_peek(0).loc);
                        }
                    }
break;
case 42:
//#line 221 "Parser.y"
{
                        yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 43:
//#line 225 "Parser.y"
{
                        yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                    }
break;
case 44:
//#line 229 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 46:
//#line 236 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 47:
//#line 242 "Parser.y"
{
                        yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
                        if (val_peek(1).loc == null) {
                            yyval.loc = val_peek(0).loc;
                        }
                    }
break;
case 48:
//#line 249 "Parser.y"
{
                        yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 49:
//#line 255 "Parser.y"
{
                        yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
                        if (val_peek(4).loc == null) {
                            yyval.loc = val_peek(3).loc;
                        }
                    }
break;
case 50:
//#line 264 "Parser.y"
{
                        yyval.expr = val_peek(0).lvalue;
                    }
break;
case 53:
//#line 270 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 54:
//#line 274 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 55:
//#line 278 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 56:
//#line 282 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 57:
//#line 286 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 58:
//#line 290 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 59:
//#line 294 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 60:
//#line 298 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 61:
//#line 302 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 62:
//#line 306 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 63:
//#line 310 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 64:
//#line 314 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 65:
//#line 318 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 66:
//#line 322 "Parser.y"
{
                        yyval = val_peek(1);
                    }
break;
case 67:
//#line 326 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 68:
//#line 330 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 69:
//#line 334 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 70:
//#line 338 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(0).loc);
                    }
break;
case 71:
//#line 342 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 72:
//#line 346 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(0).loc);
                    }
break;
case 73:
//#line 350 "Parser.y"
{
                        yyval.expr = new Tree.Ternary(Tree.TERNARY, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(3).loc);
                    }
break;
case 74:
//#line 354 "Parser.y"
{
                        yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                    }
break;
case 75:
//#line 358 "Parser.y"
{
                        yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                    }
break;
case 76:
//#line 362 "Parser.y"
{
                        yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                    }
break;
case 77:
//#line 366 "Parser.y"
{
                        yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                    }
break;
case 78:
//#line 370 "Parser.y"
{
                        yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                    }
break;
case 79:
//#line 374 "Parser.y"
{
                        yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                    }
break;
case 80:
//#line 378 "Parser.y"
{
                        yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                    }
break;
case 81:
//#line 384 "Parser.y"
{
                        yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
                    }
break;
case 82:
//#line 388 "Parser.y"
{
                        yyval.expr = new Null(val_peek(0).loc);
                    }
break;
case 84:
//#line 395 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.elist = new ArrayList<Tree.Expr>();
                    }
break;
case 85:
//#line 402 "Parser.y"
{
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 86:
//#line 406 "Parser.y"
{
                        yyval.elist = new ArrayList<Tree.Expr>();
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 87:
//#line 413 "Parser.y"
{
                        yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
                    }
break;
case 88:
//#line 419 "Parser.y"
{
                        yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
                    }
break;
case 89:
//#line 424 "Parser.y"
{
                        yyval.stmt = new Tree.RepeatLoop(val_peek(1).expr, val_peek(4).stmt, val_peek(5).loc); 
                    }
break;
case 90:
//#line 429 "Parser.y"
{
                        yyval.stmt = new Tree.Switch(val_peek(2).expr, (SwitchBlock)val_peek(0).stmt, val_peek(4).loc);
                    }
break;
case 91:
//#line 435 "Parser.y"
{
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 92:
//#line 439 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.slist = new ArrayList<Tree>();
                    }
break;
case 93:
//#line 446 "Parser.y"
{
                        yyval.stmt = val_peek(0).stmt;
                    }
break;
case 94:
//#line 450 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 95:
//#line 456 "Parser.y"
{
                        yyval.stmt = new Tree.Case(val_peek(2).expr, (CaseBlock)val_peek(0).stmt, val_peek(3).loc);
                    }
break;
case 96:
//#line 462 "Parser.y"
{
                        yyval.stmt = new Tree.Default((CaseBlock)val_peek(0).stmt, val_peek(2).loc);
                    }
break;
case 97:
//#line 468 "Parser.y"
{
                        yyval.stmt = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 98:
//#line 472 "Parser.y"
{
                        yyval.stmt = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(1).loc);
                    }
break;
case 99:
//#line 478 "Parser.y"
{
                        yyval.stmt = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 100:
//#line 482 "Parser.y"
{
                        yyval.stmt = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(1).loc);
                    }
break;
case 101:
//#line 488 "Parser.y"
{
                        yyval.stmt = new Tree.Break(val_peek(0).loc);
                    }
break;
case 102:
//#line 494 "Parser.y"
{
                        yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
                    }
break;
case 103:
//#line 500 "Parser.y"
{
                        yyval.stmt = val_peek(0).stmt;
                    }
break;
case 104:
//#line 504 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 105:
//#line 510 "Parser.y"
{
                        yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 106:
//#line 514 "Parser.y"
{
                        yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                    }
break;
case 107:
//#line 520 "Parser.y"
{
                        yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
                    }
break;
//#line 1459 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
