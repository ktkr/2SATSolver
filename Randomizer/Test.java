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

    public static long testRandom(String filePath){
        Parser p = new Parser(filePath);
        Randomizer r = new Randomizer(p.getClauses(),p.getVarNo());
        long startTime = System.currentTimeMillis();
        r.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }


    public static void main(String[] args) {
//        testCase("C:\\Users\\Acer\\AndroidStudioProjects\\TwoSat\\app\\src\\main\\java\\sutd\\twosat\\largeSat.cnf");
        long average = 0;
        for (int k = 0; k<100;k++){
            average += testRandom("C:\\Users\\Acer\\AndroidStudioProjects\\TwoSat\\app\\src\\main\\java\\sutd\\twosat\\largeSat.cnf");
        }

        System.out.println("Average running time: "+average/100);

    }
}
