package src.sat;

import src.immutable.EmptyImList;
import src.immutable.ImList;
import src.sat.env.Bool;
import src.sat.env.Environment;
import src.sat.formula.Clause;
import src.sat.formula.Formula;
import src.sat.formula.Literal;
import src.sat.formula.PosLiteral;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        Environment env = new Environment();

        for (Clause c : formula.getClauses()) {
            for (Literal l : c) {
                env = env.put(l.getVariable(), Bool.UNDEFINED);
            }
        }

        return SATSolver.solve(formula.getClauses(), env);
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {

        if (clauses.isEmpty()) {
            // a problem with no clauses is trivally satisfiable
            return env;

        } else {

            Clause smallest = clauses.first();

            // Choose arbitary literal
            Literal l = smallest.chooseLiteral();

            if (smallest.isEmpty()) {

                return null;

            } else if (smallest.isUnit()) {

                // Put it to true

                if (l instanceof PosLiteral) {
                    env = env.putTrue(l.getVariable());
                } else {
                    env = env.putFalse(l.getVariable());
                }

                env = SATSolver.solve(substitute(clauses, l), env);

                return env;

            } else {

                // Put it to true
                if (l instanceof PosLiteral) {
                    env = env.putTrue(l.getVariable());
                } else {
                    env = env.putFalse(l.getVariable());
                }
                Environment temp_env = SATSolver.solve(substitute(clauses, l), env);

                // If putting literal to true can't work
                // Put literal to false
                if (temp_env == null) {
                    // result is either an enviroment or null
                    if (l instanceof PosLiteral) {
                        env = env.putFalse(l.getVariable());
                    } else {
                        env = env.putTrue(l.getVariable());
                    }
                    env = SATSolver.solve(substitute(clauses, l), env);
                } else {
                    env = temp_env;
                }

                return env;
            }
        }
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {

        ImList<Clause> cs = new EmptyImList<Clause>();
        Clause smallest = null;

        for (Clause c : clauses) {

            c = c.reduce(l);

            if (c != null) {
                // do not add null clauses

                if (smallest == null) {
                    smallest = c;
                } else if (smallest.size() > c.size()) {
                    cs = cs.add(smallest);
                    smallest = c;
                } else {
                    cs = cs.add(c);
                }
            }

        }

        if (smallest != null){
            cs = cs.add(smallest);
        }

        return cs;

    }

}
