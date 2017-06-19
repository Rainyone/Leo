package testsend;

public class TestMain {
	public static void main(String[] args) {
		String url = "http://localhost:8080/Larva-inf/charge?body=050h2UiusmdVsjlDJaHl6dW1iBT%2Fv0VRrtKFQTR7ud3G4gavMmbM5NwqCS9CqUSiWKNLjo5JfthyUTUJPr5g6bCoWIWwN5bSH%2BoEgx48Ad76bVwNaXEWbZ6Nywkw1LTE%2Boi7GvK8JmRYkcTZlbkig6gARm36JtNJfVA%2Bdxx21%2BIxY0oGtY4W%2FyHhS6i4oPYfGKuCwj8Fq%2B%2F4MKm0xWipaqy8ePVBW2QKfQ7AkmtBGph%2FxwRdqAFvV98CnTj3A%2FKe8Dx0sXDszaaDNyQktGz96J0CCXI9gnhuQqfCgCVrciU8LjPiHS%2FkgstXFgp15iMLaVUuKkJqD7QM2u2UNxwUDbEnIF1FlvLlywQpV1H16svu6r5xopIvS5sW26wDbVamQWJof6WZsxR2LKxZHSDiC3fg0SMVV4oGOPAfHHkOLdD9TA%2BQ7QLABJ4xjSMyBjeu&sign=yR%2BroaW1Ev9l3YKwB%2F2tGS7VY8Y%3D";
		for(int i=0;i<1;i++){
			Runnable r = new SendUrl(url, "");
			Thread t = new Thread(r);
			t.setName("thread" + i);
			t.start();
			System.out.println(i);
		}
	}
}
