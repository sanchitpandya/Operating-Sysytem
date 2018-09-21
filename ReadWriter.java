public class ReadWriter {
    static int READER =0;
    static int WRITER =0;
    static int s=1;
    public static void main(String[] args) {
    
    Thread r = new Thread(new Reader());
    Thread w = new Thread(new Writer());
        /*for (int i = 0; i < WRITER; i++) {
            w.start();
        }
        for (int j = 0; j < READER; j++) {
            r.start();
        }*/
        r.start();
        w.start();
        
    }

    static class Writer implements Runnable
    {
        private int i=0;
        public void Write(int i) throws InterruptedException 
        {
            synchronized(this)
            {
                while(s!=1 || WRITER>=5)
                {
                    System.out.println("Writer is waiting because readers are reading");
                    this.wait();
                }
            }
            synchronized(this)
            {
                s=0;
                System.out.println("Writer is writing");
                WRITER++;
                Thread.sleep(500);
                s=1;
                this.notify();
                
            }
        }
        public void run()
        {
            try{
                while(true)
                {
                    i++;
                    Write(i);
                }
            }
            catch(Exception e){
                System.out.println("Interrupted Exception");
            }
        }
    }

     static class Reader implements Runnable
    {
        //private int reader = 0;
        public void Reader() throws InterruptedException 
        {
            synchronized(this)
            {
                while(s!=1 || READER>=5)
                {
                    System.out.println("Reader is waiting because writer is writing");
                    this.wait();
                }
            }
            synchronized(this)
            {
                s=0;
                System.out.println("Reader is reading");
                READER++;
                System.out.println(READER);
                Thread.sleep(500);
                s=1;
                this.notify();
                
            }
        }
        public void run()
        {
            try{
                while(true)
                {
                    Reader();
                }
            }
            catch(Exception e){
                System.out.println("Interrupted Exception");
            }
        }
    }
}