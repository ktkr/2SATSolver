package sutd.twosat;


class Test {


//    public static void testCase(String filePath) {
//        Parser p = new Parser(filePath);
//        // System.out.println(p);
//        TwoSat t = new TwoSat();
//        final long startTime = System.currentTimeMillis();
//        try {
//            t = new TwoSat(p);
//        } catch (InvalidInputException e) {
//            System.out.println("Program ended due to invalid input file!");
//        } finally {
//            System.out.println(t.solve());
//            final long endTime = System.currentTimeMillis();
//            System.out.println("Total execution time: " + (endTime - startTime) );
//        }
//    }

    public static void testRandom(String filePath){
        Parser p = new Parser(filePath);
        Randomizer r = new Randomizer(p.getClauses(),p.getVarNo());
        r.run();
    }


    public static void main(String[] args) {
//        testCase("C:\\Users\\Acer\\AndroidStudioProjects\\TwoSat\\app\\src\\main\\java\\sutd\\twosat\\largeSat.cnf");
            testRandom("C:\\Users\\Acer\\AndroidStudioProjects\\TwoSat\\app\\src\\main\\java\\sutd\\twosat\\largeSat.cnf");

    }
}
