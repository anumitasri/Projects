import java.util.Random;

public class track {

	static boolean debug = false;
	static double alpha = 0.3;
	public double xr[] = new double[1000];
	public double yr[] = new double[1000];
	public double zr[] = new double[1000];
	public int trackSeq[] = new int[200]; // The array
	public int trackLocation = 0;
	public int direction = 1;
	Random rand = new Random();
	public int repairspace=23;
	public int indexGlobal;
	public int loops=10;
	public int climbs=5;
	

	public void debugprint(String s) {
		if (debug)
			System.out.println(s);

	}

	public boolean testz()
	{
		if(zr[indexGlobal]<=0&&indexGlobal>5)
		{
			System.out.println("Founf z =0");
			return false;
		}
		else
			return true;
	}
	/*************************************************************************************
	 * Function to mutate the track no Args
	 */
	public void mutate() {

		debugprint("Mutation in Progress!!");

		int l = rand.nextInt(trackLocation);

		int t = rand.nextInt(17);
		switch (t) {
		case 1:
			this.trackSeq[l] = 11;
			break;
		case 2:
			this.trackSeq[l] = 21;
			break;
		case 3:
			this.trackSeq[l] = 22;
			break;
		case 4:
			this.trackSeq[l] = 33;
			break;
		case 5:
			this.trackSeq[l] = 32;
			break;
		case 6:
			this.trackSeq[l] = 31;
			break;
		case 7:
			this.trackSeq[l] = 41;
			break;
		case 8:
			this.trackSeq[l] = 42;
			break;
		case 9:
			this.trackSeq[l] = 43;
			break;
		case 10:
			this.trackSeq[l] = 23;
			break;
		case 11:
			this.trackSeq[l] = 52;
			break;
		case 12:
			this.trackSeq[l] = 53;
			break;
		case 13:
			this.trackSeq[l] = 61;
			break;
		case 14:
			this.trackSeq[l] = 62;
			break;
		case 15:
			this.trackSeq[l] = 63;
			break;
		case 16:
			this.trackSeq[l] = 51;
			break;
		case 17:
			this.trackSeq[l] = 71;
			break;
			

		}

	}

	public void crossover(int[] parent2) {

		debugprint("XOver in progress");
		int n = rand.nextInt(49) + 2;
		while (n <= 49) {
			n = rand.nextInt(49) + 2;
		}
		int child1[] = new int[trackSeq.length];
		int child2[] = new int[trackSeq.length];
		child1[0] = child2[0] = 11;
		child1[1] = child2[1] = 43;

		for (int i = 2; i < n; i++) // track size presnt
		{
			child1[i] = trackSeq[i];
			child2[i] = parent2[i];
		}
		for (int i = n; i < trackSeq.length; i++) {
			child1[i] = parent2[i];
			child2[i] = trackSeq[i];
		}
		if (fitnessCal2(1, child1) > fitnessCal2(1, child2)) {
			trackSeq = child1;
		} else if (fitnessCal2(1, child1) <= fitnessCal2(1, child2)) {
			trackSeq = child2;
		}

		debugprint("XOver Done");
	}

	public void repair(int index) {
		

		index= repairForce(index);
		

	}
	public int repairForce(int index){

		
		
		int i= index;
		//if (xr[i]!=0 &&yr[i]!=0 && zr[i]!=0)
		{
			xr[i] = 0;
		yr[i] = 0;
		zr[i] = 0;
		index++;
		
		return i;}

		
		
	
}

	public int createTrack() {
		
		
		
		int index = 0;
		int counter = trackSeq.length;
		int r = rand.nextInt(100);
		index = trackInit();
		while (counter > 0 && testz()) {
			if (r >= 0 && r <= 15) {
				index = straight11(index);
				counter--;
			}
			if (r > 15 && r <= 21) {
				index = rightCurve21(index);
				counter--;
			}
			if (r > 21 && r <= 29) {
				index = rightCurve22(index);
				counter--;
			}
			if (r > 29 && r <= 39) {
				index = rightCurve23(index);
				counter--;
			}
			if (r > 39 && r <= 42) {
				index = leftCurve31(index);
				counter--;
			}
			if (r > 42 && r <= 46) {
				index = leftCurve32(index);
				counter--;
			}
			if (r > 46 && r <= 51) {
				index = leftCurve33(index);
				counter--;
			}
			if (r > 51 && r <= 55) {
				index = hillClimb41(index);
				counter--;
			}
			if (r > 55 && r <= 58) {
				index = hillClimb42(index);
				counter--;
			}
			if (r > 58 && r <= 60) {
				index = hillClimb43(index);
				counter--;
			}
			if (r > 60 && r <= 64) {
				index = downSlope51(index);
				counter--;
			}
			if (r > 64 && r <= 69) {
				index = downSlope52(index);
				counter--;
			}
			if (r > 69 && r <= 75) {
				index = downSlope53(index);
				counter--;
			}
			if (r > 75 && r <= 80) {
				index = loop61(index);
				counter--;
			}
			if (r > 80 && r <= 84) {
				index = loop62(index);
				counter--;
			}
			if (r > 84 && r <= 86) {
				index = loop63(index);
				counter--;
			}
			if (r > 86 && r <= 88) {
				index = loop63(index);
				counter--;
			}
			if (r > 88 && r <= 91) {
				index = leftCurve31(index);
				counter--;
			}
			if (r > 91 && r <= 95) {
				index = leftCurve32(index);
				counter--;
			}
			if (r > 95 && r <= 100) {
				index = straight11(index);
				counter--;
			}

			r = rand.nextInt(100);
		}

		// print(index);
		repair(index);
		return index;
	}

	/*************************************************************************************
	 * Function gets fitness of the object
	 * 
	 * @param enter
	 *            1 for adult and 2 for child
	 */
	public double fitnessCal(int person) {
		double fitness1 = 0;
		int loops=10,climbs=20;
		if (person == 1) {
			for (int i = 0; i < trackSeq.length; i++) {
				switch (trackSeq[i]) {
				case 11:
					fitness1 += 0;
					break;
				case 21:
				case 31:
					fitness1 += 10;
					break;
				case 22:
				case 32:
					fitness1 += 12.5;
					break;
				case 23:
				case 33:
					fitness1 += 15;
					break;
				case 41:
				case 42:
				case 43:
					if(climbs>=0)
					{
					fitness1 += 5;
					climbs--;
					}
					else
						fitness1 -=50;
				case 51:
					fitness1 += 20;
					break;
				case 52:
					fitness1 += 23;
					break;
				case 53:
					fitness1 += 25;
					break;
				case 61:
					if(loops>=0)
					{
					fitness1 += 25;
					loops--;
					}
					else
						fitness1 =0;
					break;
				case 62:
					if(loops>=0)
					{
					fitness1 += 30;
					loops--;
					}
					else
					{
						fitness1=0;
					}
					break;
				case 63:
					if(loops>=0)
					{
					fitness1 += 35;
					loops--;
					}
					else
					{
						fitness1=0;
					}
					
					break;
				case 71:
					if(loops>=0)
					{
					fitness1 += 40;
					loops--;
					}
					else
					{
						fitness1=0;
					}
					break;
				default:
					fitness1 += 0; // this means the passenger will die for sure
					break;

				}

			}
			return fitness1;
		} else if (person == 2) {
			return fitness1;
		} else {
			System.out.println("enter correct person!!");
			return -1;
		}

	}

	public double fitnessCal2(int person, int[] trackSeq) {
		double fitness1 = 0;

		if (person == 1) {
			for (int i = 0; i < trackSeq.length; i++) {
				switch (trackSeq[i]) {
				case 11:
					fitness1 += 0;
					break;
				case 21:
				case 31:
					fitness1 += 10;
					break;
				case 22:
				case 32:
					fitness1 += 12.5;
					break;
				case 23:
				case 33:
					fitness1 += 15;
					break;
				case 41:
				case 42:
				case 43:
					fitness1 += 5;
				case 51:
					fitness1 += 20;
					break;
				case 52:
					fitness1 += 23;
					break;
				case 53:
					fitness1 += 25;
					break;
				case 61:
					fitness1 += 25;
					break;
				case 62:
					fitness1 += 30;
					break;
				case 63:
					fitness1 += 35;
					break;
				case 71:
					fitness1 += 40;
					break;
				default:
					fitness1 += 0; // this means the passenger will die for sure
					break;

				}

			}
			return fitness1;
		}

		else if (person == 2) {
			for (int i = 0; i < trackSeq.length; i++) {
				switch (trackSeq[i]) {
				case 11:
					fitness1 += 0;
					break;
				case 21:
				case 31:
					fitness1 += 10;
					break;
				case 22:
				case 32:
					fitness1 += 12.5;
					break;
				case 23:
				case 33:
					fitness1 += 15;
					break;
				case 41:
				case 42:
				case 43:
					fitness1 += 5;
				case 51:
					fitness1 += 20;
					break;
				case 52:
					fitness1 += 23;
					break;
				case 53:
					fitness1 += 25;
					break;
				case 61:
					fitness1 -= 10;
					break;
				case 62:
					fitness1 -= 15;
					break;
				case 63:
					fitness1 -= 20;
					break;
				case 71:
					fitness1 -= 20;
					break;
				default:
					fitness1 += 0; // this means the passenger will die for sure
					break;

				}
			}
			return fitness1;

		} else {
			System.out.println("enter correct person!!");
			return -1;
		}

	}

	public void print(int index) {
		for (int i = 0; i <= indexGlobal; i++) {
			System.out.println(xr[i] + "\t" + yr[i] + "\t" + zr[i]);
		}

	}

	public void trackInit2(int r) // initialize the track
	{
		double x, y, z;
		int xi = 0, yi = 0, zi = 0;

		double a = 360;
		z = 0;
		while (a >= 0) {

			x = r * Math.cos(a);
			y = r * Math.sin(a);
			// if(a>270)
			z = 1;
			a = a - 7;

			xr[xi++] = x;
			yr[yi++] = y;
			zr[zi++] = z;

			// System.out.println("Y:"+y);

		}
	}

	public int trackInit() // initialize the track
	{
		
		
		
		xr[0] = 0;
		yr[0] = 0;
		zr[0] = 0;

		xr[1] = 0;
		yr[1] = 10;
		zr[1] = 0;
		trackSeq[trackLocation++] = 11;

		xr[2] = 0;
		yr[2] = 20;
		zr[2] = 50;
		trackSeq[trackLocation++] = 43;
		
		xr[3] = 0;
		yr[3] = 30;
		zr[3] = 35;

		trackSeq[trackLocation++] = 53;
		for(int i =4 ;i<200;i++)
		{
			xr[i] = 0;
			yr[i] = 0;
			zr[i] = 0;
		}
			
		
		return 3;

	}

	/*************************************************************************************
	 * Straight track creation with creation code 11 will look behind to check
	 * the direction
	 * 
	 * @param xi
	 *            is the index in array x
	 * @param yi
	 *            is the index in array y
	 * @param zi
	 *            is the index in array z
	 */
	public int straight11(int index) {
		int xi = index, zi = index, yi = index;

		xr[xi + 1] = xr[xi] + (xr[xi] - xr[xi - 1]);
		yr[yi + 1] = yr[xi] + (yr[xi] - yr[xi - 1]);
		zr[zi + 1] = zr[zi];

		return index + 1;

	}

	public int rightCurve21(int index) {
		// First point of the curve
		if (direction == 1) {
			debugprint("First point of the curve21");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] + 3;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] + 5;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 2;
		} else if (direction == 2) {
			debugprint("First point of the curve21");
			xr[index + 1] = xr[index] + 3;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] + 5;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 3;
		} else if (direction == 3) {
			debugprint("First point of the curve21  err");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] - 3;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] - 5;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 4;

		} else if (direction == 4) {
			debugprint("First point of the curve21  err");
			xr[index + 1] = xr[index] - 3;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] - 5;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 1;

		}

		/*
		 * xr[index+1]=xr[index]+(xr[index]-xr[index-1])+1;
		 * yr[index+1]=yr[index]+(yr[index]-yr[index-1])+3;
		 * zr[index+1]=zr[index];
		 * 
		 * xr[index+2]=xr[index+1]+(xr[index+1]-xr[index])+3;
		 * yr[index+2]=yr[index+1]+(yr[index+1]-yr[index])+5;
		 * zr[index+2]=zr[index+1];
		 * 
		 * 
		 * xr[index+3]=xr[index+2]+(xr[index+2]-xr[index+1])+5;
		 * yr[index+3]=yr[index+2]+(yr[index+2]-yr[index+1])+5;
		 * zr[index+3]=zr[index+2];
		 */

		trackSeq[trackLocation++] = 21;
		indexGlobal=index+3;
		return index + 3;

	}

	public int rightCurve22(int index) {
		// First point of the curve
		if (direction == 1) {
			debugprint("First point of the curve22");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] + 2;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] + 4;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 2;
		} else if (direction == 2) {
			debugprint("First point of the curve22");
			xr[index + 1] = xr[index] + 2;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] + 4;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 3;
		} else if (direction == 3) {

			debugprint("First point of the curve22");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] - 2;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] - 4;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 4;
		} else if (direction == 4) {
			debugprint("First point of the curve22");
			xr[index + 1] = xr[index] - 2;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] - 4;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
		}
		trackSeq[trackLocation++] = 22;
		indexGlobal=index+3;
		return index + 3;
	}

	public int rightCurve23(int index) {
		// First point of the curve
		if (direction == 1) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 2;
		} else if (direction == 2) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 3;
		} else if (direction == 3) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 4;
		} else if (direction == 4) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 1;
		}
		trackSeq[trackLocation++] = 23;
		indexGlobal=index+3;
		return index + 3;

	}

	public int leftCurve31(int index) {
		// First point of the curve
		if (direction == 1) {
			debugprint("First point of the curve31");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] + 3;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] + 5;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 2;
		} else if (direction == 4) {
			debugprint("First point of the curve21  err");
			xr[index + 1] = xr[index] + 3;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] + 5;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 1;
		} else if (direction == 3) {
			debugprint("First point of the curve21  err");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] - 3;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] - 5;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 4;

		} else if (direction == 2) {
			debugprint("First point of the curve21  err");
			xr[index + 1] = xr[index] - 3;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve21");
			xr[index + 2] = xr[index] - 5;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve21");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 3;

		}

		trackSeq[trackLocation++] = 31;
		indexGlobal=index+3;
		return index + 3;

	}

	public int leftCurve32(int index) {
		// First point of the curve
		if (direction == 1) {
			debugprint("First point of the curve32");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] + 2;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] + 4;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 2;
		} else if (direction == 4) {
			debugprint("First point of the curve22");
			xr[index + 1] = xr[index] + 2;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] + 4;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 1;
		} else if (direction == 3) {

			debugprint("First point of the curve22");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] - 2;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] - 4;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 4;
		} else if (direction == 2) {
			debugprint("First point of the curve22");
			xr[index + 1] = xr[index] - 2;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve22");
			xr[index + 2] = xr[index] - 4;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve22");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 3;
		}
		trackSeq[trackLocation++] = 32;
		indexGlobal=index+3;
		return index + 3;
	}

	public int leftCurve33(int index) {
		// First point of the curve
		if (direction == 1) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 2;
		} else if (direction == 4) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] + 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] + 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] + 5;
			zr[index + 3] = zr[index];
			direction = 1;
		} else if (direction == 3) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] + 1;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] + 3;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] + 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 4;
		} else if (direction == 2) {
			debugprint("First point of the curve23");
			xr[index + 1] = xr[index] - 1;
			yr[index + 1] = yr[index] - 1;
			zr[index + 1] = zr[index];

			// third point of the curve

			debugprint("2nd point of the curve23");
			xr[index + 2] = xr[index] - 3;
			yr[index + 2] = yr[index] - 3;
			zr[index + 2] = zr[index];

			// forth point of the curve

			debugprint("3rd point of the curve23");
			xr[index + 3] = xr[index] - 5;
			yr[index + 3] = yr[index] - 5;
			zr[index + 3] = zr[index];
			direction = 3;
		}
		trackSeq[trackLocation++] = 33;
		indexGlobal=index+3;
		return index + 3;

	}

	public int hillClimb41(int index) {
		int i = index;
		if(climbs>=0)
		{
		xr[i + 1] = xr[i] + (xr[i] - xr[i - 1]);
		yr[i + 1] = yr[i] + (yr[i] - yr[i - 1]);
		zr[i + 1] = zr[i] + 5;

		trackSeq[trackLocation++] = 41;
		indexGlobal=index+1;
		climbs--;
		return index + 1;
		
		}
		else
			return index;

	}

	public int hillClimb42(int index) {
		int i = index;
		if(climbs>=0)
		{
		xr[i + 1] = xr[i] + (xr[i] - xr[i - 1]);
		yr[i + 1] = yr[i] + (yr[i] - yr[i - 1]);
		zr[i + 1] = zr[i] + 6;

		trackSeq[trackLocation++] = 42;
		indexGlobal=index+1;
		climbs--;
		return index + 1;
		
		}
		else
			return index;
	}

	public int hillClimb43(int index) {
		int i = index;
		if(climbs>=0)
		{
		xr[i + 1] = xr[i] + (xr[i] - xr[i - 1]);
		yr[i + 1] = yr[i] + (yr[i] - yr[i - 1]);
		zr[i + 1] = zr[i] + 7;

		trackSeq[trackLocation++] = 43;
		indexGlobal=index+1;
		climbs--;
		return index + 1;
		
		}
		else
			return index;

	}

	public int downSlope51(int index) {
		int i = index;
		xr[i + 1] = xr[i] + (xr[i] - xr[i - 1]);
		yr[i + 1] = yr[i] + (yr[i] - yr[i - 1]);
		zr[i + 1] = zr[i] - 5;

		trackSeq[trackLocation++] = 51;
		if(zr[i+1]<0)
		{
			return index;
			
		}
		else
		{
		indexGlobal=index+1;
		return index + 1;
		}

	}

	public int downSlope52(int index) {
		int i = index;
		xr[i + 1] = xr[i] + (xr[i] - xr[i - 1]);
		yr[i + 1] = yr[i] + (yr[i] - yr[i - 1]);
		zr[i + 1] = zr[i] - 2;

		trackSeq[trackLocation++] = 52;
		if(zr[i+1]<0)
		{
			return index;
			
		}
		else
		{
		indexGlobal=index+1;
		return index + 1;
		}

	}

	public int downSlope53(int index) {
		int i = index;
		xr[i + 1] = xr[i] + (xr[i] - xr[i - 1]);
		yr[i + 1] = yr[i] + (yr[i] - yr[i - 1]);
		zr[i + 1] = zr[i] - 1;

		trackSeq[trackLocation++] = 53;
		if(zr[i+1]<0)
		{
			return index;
			
		}
		else
		{
		indexGlobal=index+1;
		return index + 1;
		}

	}

	public int loop61(int index) {

		int i = 1;
		if(loops>=0)
		{
		// First point of the curve
		if (direction == 1) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index] + 2;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index];

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index] + 4;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index];
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index] + 4.5;
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index];
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index] + 4;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index];
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 3;
			zr[index + i] = zr[index] + 4.5;
			xr[index + i] = xr[index] + 0.5;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 2;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1.5;
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 2;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 4;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 6;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;

			direction = 1;
		}

		else if (direction == 2) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 2;

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 4;
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index] + 4.5;
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 4;
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 0.5;
			zr[index + i] = zr[index] + 4.5;
			xr[index + i] = xr[index] + 3;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 2;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index] + 1.5;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 2;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 4;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 6;
			direction = 2;
		}

		else if (direction == 3) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index] - 2;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index];

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index] - 4;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index];
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index] - 4.5;
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index];
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index] - 4;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index];
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] - 3;
			zr[index + i] = zr[index] + 4.5;
			xr[index + i] = xr[index] + 0.5;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] - 2;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] - 1.5;
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] - 2;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] - 4;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] - 6;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			direction = 3;
		}

		else if (direction == 4) {

			debugprint("First point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 2;

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] - 4;
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index] - 4.5;
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] - 4;
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 0.5;
			zr[index + i] = zr[index] + 4.5;
			xr[index + i] = xr[index] - 3;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] - 2;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 2;
			xr[index + i] = xr[index] - 1.5;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] - 2;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 4;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 6;

			direction = 4;
		}
		trackSeq[trackLocation++] = 63;
		indexGlobal=index+i;
		loops--;
		return index + i;
		
		}
		else
			return index;

	}

	public int loop62(int index) {

		int i = 1;
		// First point of the curve
		if(loops>=0)
		{
		if (direction == 1) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index];

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index] + 4;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index];
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index] + 4.5;
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index];
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index] + 4;
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index];
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 3;
			zr[index + i] = zr[index] + 6;
			xr[index + i] = xr[index] + 0.5;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 2;
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1.5;
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 2;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 5;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 6;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;

			direction = 1;
		}

		else if (direction == 2) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 4;
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index] + 4.5;
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index] + 4;
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 0.5;
			zr[index + i] = zr[index] + 6;
			xr[index + i] = xr[index] + 3;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index] + 2;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index] + 1.5;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 2;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 5;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 6;
			direction = 2;
		}

		else if (direction == 3) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index] - 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index];

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index] - 4;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index];
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index] - 4.5;
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index];
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index] - 4;
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index];
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] - 3;
			zr[index + i] = zr[index] + 6;
			xr[index + i] = xr[index] + 0.5;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] - 2;
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] - 1.5;
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] - 2;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] - 5;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] - 6;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			direction = 3;
		}

		else if (direction == 4) {

			debugprint("First point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 1;

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] - 4;
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index] - 4.5;
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index] - 4;
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 0.5;
			zr[index + i] = zr[index] + 6;
			xr[index + i] = xr[index] - 3;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 5;
			xr[index + i] = xr[index] - 2;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 3;
			xr[index + i] = xr[index] - 1.5;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] - 2;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 5;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 6;

			direction = 4;
		}
		trackSeq[trackLocation++] = 63;
		indexGlobal=index+i;
		loops--;
		return index + i;
		
		}
		else
			return index;

	}

	public int loop63(int index) {
		int i = 1;
		// First point of the curve
		if(loops>=0)
		{
		if (direction == 1) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index];

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index] + 4;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index];
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index] + 4.5;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index];
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index] + 4;
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index];
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 3;
			zr[index + i] = zr[index] + 8;
			xr[index + i] = xr[index] + 0.5;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 2;
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1.5;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 2;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 5;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 6;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;

			direction = 1;
		}

		else if (direction == 2) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 4;
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 4.5;
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index] + 4;
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 0.5;
			zr[index + i] = zr[index] + 8;
			xr[index + i] = xr[index] + 3;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index] + 2;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 1.5;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 2;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 5;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 6;
			direction = 2;
		}

		else if (direction == 3) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index] - 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index];

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index] - 4;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index];
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index] - 4.5;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index];
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index] - 4;
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index];
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] - 3;
			zr[index + i] = zr[index] + 8;
			xr[index + i] = xr[index] + 0.5;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] - 2;
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] - 1.5;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] - 2;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] - 5;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] - 6;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] + 1;
			direction = 3;
		}

		else if (direction == 4) {
			debugprint("First point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 1;

			i++;
			// third point of the curve

			debugprint("2nd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] - 4;
			i++;

			// forth point of the curve

			debugprint("3rd point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] - 4.5;
			i++;

			debugprint("4th point of the curve63");
			yr[index + i] = yr[index];
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index] - 4;
			i++;

			debugprint("5th point of the curve63");
			yr[index + i] = yr[index] + 0.5;
			zr[index + i] = zr[index] + 8;
			xr[index + i] = xr[index] - 3;
			i++;

			debugprint("6th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 6.5;
			xr[index + i] = xr[index] - 2;
			i++;

			debugprint("7th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 4;
			xr[index + i] = xr[index] - 1.5;
			i++;

			debugprint("8th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index] + 1;
			xr[index + i] = xr[index] - 2;
			i++;

			debugprint("9th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 5;
			i++;

			debugprint("10th point of the curve63");
			yr[index + i] = yr[index] + 1;
			zr[index + i] = zr[index];
			xr[index + i] = xr[index] - 6;
			direction = 4;
		}
		trackSeq[trackLocation++] = 63;
		indexGlobal=index+i;
		loops--;
		return index + i;
		
		}
		else
			return index;
		

	}

	public int springRoll(int index) {

		//int i = index;
		int i = index;
		xr[i + 1] = xr[i] + (xr[i] - xr[i - 1]);
		yr[i + 1] = yr[i] + (yr[i] - yr[i - 1]);
		zr[i + 1] = zr[i] - 15;

		trackSeq[trackLocation++] = 53;
		if(zr[i+1]<0)
		{
			return index;
			
		}
		else
		{
		indexGlobal=index+1;
		return index + 1;
		}
		

	}

}
