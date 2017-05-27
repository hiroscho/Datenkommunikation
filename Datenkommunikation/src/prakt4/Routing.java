package prakt4;

import java.util.Arrays;

public class Routing {

	private enum Router {
		A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9), K(10), L(11), M(12);

		private final int id;

		private Router(int n) {
			id = n;
		}

		public int getValue() {
			return id;
		}

	}

	private static String[] head = new String[Router.values().length];
	private static Router[] target = new Router[Router.values().length];
	private static int[][] distance = new int[head.length][target.length];
	private static Router[][] next = new Router[head.length][target.length];

	private static String[] iA = { "B1", "F4", "G5" };
	private static String[] iB = { "A1", "C1", "D1", "E2" };
	private static String[] iC = { "B1", "E1" };
	private static String[] iD = { "B1", "E1", "F3" };
	private static String[] iE = { "B2", "C1", "D1", "G1", "L4" };
	private static String[] iF = { "A4", "D3", "L6" };
	private static String[] iG = { "A5", "E1", "H3", "J4", "L5" };
	private static String[] iH = { "G3", "I2" };
	private static String[] iI = { "H2", "K3" };
	private static String[] iJ = { "G4", "K1", "L1", "M3" };
	private static String[] iK = { "I3", "J1" };
	private static String[] iL = { "E4", "F6", "G5", "J1", "M1" };
	private static String[] iM = { "J3", "L1" };
	private static String[][] tables = { iA, iB, iC, iD, iE, iF, iG, iH, iI, iJ, iK, iL, iM };

	private static int rounds = 0;

	public static void main(String[] args) {
		for (int[] row : distance) {
			Arrays.fill(row, -1);
		}
		init();
		while (doRound())
			; // do a round until nothing changed

		System.out.println("\n---------------------------------------\n End result, " + (rounds - 1) + " rounds!\n");
		printTables();
		// printTableAt(Router.M.getValue());
	}

	private static boolean doRound() {
		rounds++;
		// Represents change in this specific round
		boolean totalChange = false;

		// loop through head
		for (int i = 0; i < head.length; i++) {
			// If we enter the change directly there will be cases in which it
			// will use one of the newly added entries to route. This is
			// possible here but not in reality because we wouldn't have it's
			// routing table. This is why we create a temporary copy.
			int[] tempDist = distance[i].clone();

			// Represents change in this specific table
			boolean localChange = false;

			// loop through target
			for (int j = 0; j < target.length; j++) {
				if (distance[i][j] > 0) {
					// loop through target of head[j]
					for (int k = 0; k < target.length; k++) {
						if (distance[j][k] > 0) {
							int totalDist = distance[i][j] + distance[j][k];
							// distance hasn't been set yet
							if (distance[i][k] < 0) {
								tempDist[k] = totalDist;
								next[i][k] = target[j];
								totalChange = localChange = true;
								continue;
							}
							if (distance[i][k] > totalDist) {
								tempDist[k] = totalDist;
								next[i][k] = target[j];
								totalChange = localChange = true;
							}
							//ignore 0
						}
					}
				}
			}
			if (localChange) {
				// Write change to the real table
				distance[i] = tempDist.clone();
				// Change the header to represent which round this was
				String t = head[i];
				head[i] = t.substring(0, 1) + Integer.toString(rounds);
			}
		}

		printTables();
		return totalChange;
	}

	private static void init() {
		Router[] roVal = Router.values();
		for (int i = 0; i < roVal.length; i++) {
			head[i] = roVal[i] + Integer.toString(rounds);
			target[i] = roVal[i];
			distance[i][i] = 0;
			next[i][i] = roVal[i];
		}

		for (int i = 0; i < tables.length; i++) {
			for (String neighbor : tables[i]) {
				Router router = Router.valueOf(neighbor.substring(0, 1));
				int dist = Integer.parseInt(neighbor.substring(1, 2));
				distance[i][router.getValue()] = dist;
				next[i][router.getValue()] = router;
			}
		}
	}

	private static void printTables() {
		for (int i = 0; i < head.length; i++) {
			System.out.println(head[i]);
			for (int j = 0; j < target.length; j++) {
				System.out.println(target[j] + "\t" + distance[i][j] + "\t" + next[i][j]);
			}
			System.out.println();
		}
	}

	private static void printTableAt(int i) {
		System.out.println(head[i]);
		for (int j = 0; j < target.length; j++) {
			System.out.println(target[j] + "\t" + distance[i][j] + "\t" + next[i][j]);
		}
	}
}
