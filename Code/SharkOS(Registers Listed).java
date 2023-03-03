package sharkmachine;

// Used for reading the .txt files
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class SharkOS {
	
	// Memory is defined
	String [] MEMORY;
	
	// Program files are defined and assigned to variable to be called from.
	String[] programfiles = {"sharkosprog1.txt", "sharkosprog2.txt"};
	
	// Variable initiated to keep track of the program file being called.
	int progcount = 0;
	
	// Variable initiated to keep track of the next available space in the memory where instructions can be stored.
	int memorycount = 210;
	
	// The registers are defined
	int ACC;
	int PSIAR;
	int SAR;
	int SDR;
	int TMPR;
	int CSIAR;
	int IR;
	int MIR;
	
	// (Used for testing purposes) Variable if set to 1 will display the registers at each instruction, if set to 0 it will not.
	int info = 1;
	
	
	public SharkOS() {
		
		// Initilizes the memory array to the size of 1024
		MEMORY = new String [1024];
		
		// Sets PSIAR to the starting assigned space in the memory.
		PSIAR = 210;
		
		// Initilizes the system,
		INIT_SYSTEM();
		
	}


	private void INIT_SYSTEM() {
		
		// Calls the function to run all the instructions and processes.
		RUN_SHARKS();
		EXIT_SHARKOS();
		
	}
	
	public void LOAD_SHARKOS_PROGRAMS( String file) {
		
		// Reads through each line of the .txt files and adds it to the memory according to memory count (the next available space)
		try{
			File programs = new File(file);
			Scanner reader = new Scanner(programs);
			while (reader.hasNextLine()) {
				String pinfo = reader.nextLine();
				MEMORY[memorycount] = pinfo;
				
				//Variable is incremented by one to represent the next available space.
				memorycount++;
				
			}
			reader.close();
		} catch (FileNotFoundException e){
			System.out.println("An error");
		}

	}
	

	private void RUN_SHARKS() {
		
		// While loop that ensures that each of the program files are ran before continuing.
		while (progcount < programfiles.length) {
			
			// Prints what program file is being ran.
			System.out.println("Loading:" + programfiles[progcount]);
			System.out.println("");
			
			// sets the PSIAR to the last open space in memory so that any new instructions pulled can be added below the previous
			PSIAR = memorycount;
			
			// Loads all the instructions in the text file to memory to be used.
			LOAD_SHARKOS_PROGRAMS(programfiles[progcount]);
			
			// Incremented by 1 to show that one of the total files has been loaded.
			progcount++;
			
			// Variable to represent the operation code of the first instruction in the file to go into the loop.
			String opcode = MEMORY[PSIAR].split("\\s")[0];

			// Defines the overall loop so HALT can break out of it, when called.
		loop2:
			
			// While loop to ensure each instruction is processed.
			while (PSIAR != memorycount || opcode.equals("HALT")) {
				
				// opcode is set the the operation in the instruction line at each loop
				opcode = MEMORY[PSIAR].split("\\s")[0];
				
				// depending on what the operation is that is stated in the opcode, the switch case calls that matching function.
				if (opcode.equals("ADD")){
					ADD();
				} else if (opcode.equals("LDI")) {
					LDI();
				} else if (opcode.equals("STR")) {
					STR();
				} else if (opcode.equals("LDA")) {
					LDA();
				} else if (opcode.equals("SUB")) {
					SUB();
				} else if (opcode.equals("CBR")) {
					CBR();
				} else if (opcode.equals("BRH")) {
					BRH();
				} else if (opcode.equals("END")) {
					END();
				} else {
					HALT();
					// Breaks out of the overall loop
					break loop2;
					
				}
				
			
		}
		
			
		
		}
		
	}
	
	private void END() {
		
		// Lists all the registers and memory as project guidelines requested.
		System.out.println ("Job Completed.");
		
		System.out.println("Registers:");
		System.out.println("ACC= " + ACC);
		System.out.println("PSIAR= " + PSIAR);
		System.out.println("SAR= " + SAR);
		System.out.println("SDR= " + SDR);
		System.out.println("TMPR= " + TMPR);
		System.out.println("+++++++++++++++++++++");
		System.out.println("Memory:");
		System.out.println(Arrays.deepToString(MEMORY));
		System.out.println("+++++++++++++++++++++");
		System.out.println("");
		
		TMPR = ACC;
		ACC = PSIAR+1; 
		PSIAR = ACC;
		ACC = TMPR;
		
		
	}


	private void HALT() {
		
		if (info == 1) {
			System.out.println(PSIAR + " "+ MEMORY[PSIAR]);
			System.out.println("Registers:");
			System.out.println("ACC= " + ACC);
			System.out.println("PSIAR= " + PSIAR);
			System.out.println("SAR= " + SAR);
			System.out.println("SDR= " + SDR);
			System.out.println("TMPR= " + TMPR);
			System.out.println("+++++++++++++++++++++");
			System.out.println("");
			}
	}


	private void ADD(){
		
		SAR = Integer.parseInt(MEMORY[PSIAR].split("\\s")[1]);
		TMPR = ACC;
		ACC = PSIAR+1; 
		PSIAR = ACC;
		ACC = TMPR;
		SDR = Integer.parseInt(MEMORY[SAR]); 
		TMPR = SDR; 
		ACC = ACC + TMPR; 
		CSIAR = 0;
		
		if (info == 1) {
			System.out.println(PSIAR + " "+ MEMORY[PSIAR-1]);
			System.out.println("Registers:");
			System.out.println("ACC= " + ACC);
			System.out.println("PSIAR= " + PSIAR);
			System.out.println("SAR= " + SAR);
			System.out.println("SDR= " + SDR);
			System.out.println("TMPR= " + TMPR);
			System.out.println("+++++++++++++++++++++");
			System.out.println("");
			}
		
		
		
	}
	
	private void SUB() {
		
		
		
		SAR = Integer.parseInt(MEMORY[PSIAR].split("\\s")[1]);
		TMPR = ACC; 
		ACC = PSIAR+1; 
		PSIAR = ACC;
		ACC = TMPR; 
		SDR = Integer.parseInt(MEMORY[SAR]); 
		TMPR = SDR; 
		ACC = ACC - TMPR; 
		CSIAR = 0;
		
		if (info == 1) {
			System.out.println(PSIAR + " "+ MEMORY[PSIAR-1]);
			System.out.println("Registers:");
			System.out.println("ACC= " + ACC);
			System.out.println("PSIAR= " + PSIAR);
			System.out.println("SAR= " + SAR);
			System.out.println("SDR= " + SDR);
			System.out.println("TMPR= " + TMPR);
			System.out.println("+++++++++++++++++++++");
			System.out.println("");
			}
	
	}
	private void LDA() {
		
	
	
		
		SDR = Integer.parseInt(MEMORY[PSIAR].split("\\s")[1]);
		ACC = PSIAR +1; // ACC = 215
		PSIAR = ACC;  // PSIAR = 215
		TMPR = SDR; // TMPR = 200
		SAR = TMPR; // SAR = 200
		SDR = Integer.parseInt(MEMORY[SAR]); // SDR = 100
		ACC = SDR;
		CSIAR = 0;
		
		if (info == 1) {
			System.out.println(PSIAR + " "+ MEMORY[PSIAR-1]);
			System.out.println("Registers:");
			System.out.println("ACC= " + ACC);
			System.out.println("PSIAR= " + PSIAR);
			System.out.println("SAR= " + SAR);
			System.out.println("SDR= " + SDR);
			System.out.println("TMPR= " + TMPR);
			System.out.println("+++++++++++++++++++++");
			System.out.println("");
			}
	}
	private void STR(){
		
	
			
		SAR = Integer.parseInt(MEMORY[PSIAR].split("\\s")[1]); 
		TMPR = ACC; // TMPR = 100
		ACC = PSIAR+1; // ACC = 212
		PSIAR = ACC; //PSIAR = 212
		ACC = TMPR; //ACC = 100
		SDR = ACC;
		TMPR = SAR; 
		MEMORY[SAR] = SDR+"";
		CSIAR = 0;
		
		if (info == 1) {
			System.out.println(PSIAR + " "+ MEMORY[PSIAR-1]);
			System.out.println("Registers:");
			System.out.println("ACC= " + ACC);
			System.out.println("PSIAR= " + PSIAR);
			System.out.println("SAR= " + SAR);
			System.out.println("SDR= " + SDR);
			System.out.println("TMPR= " + TMPR);
			System.out.println("+++++++++++++++++++++");
			System.out.println("");
			}
	}
	private void BRH() {
		
		
		SDR = Integer.parseInt(MEMORY[PSIAR].split("\\s")[1]);
		SAR = PSIAR;
		PSIAR = SDR;
		CSIAR = 0;
		
		if (info == 1) {
			System.out.println(PSIAR + " "+ MEMORY[PSIAR]);
			System.out.println("Registers:");
			System.out.println("ACC= " + ACC);
			System.out.println("PSIAR= " + PSIAR);
			System.out.println("SAR= " + SAR);
			System.out.println("SDR= " + SDR);
			System.out.println("TMPR= " + TMPR);
			System.out.println("+++++++++++++++++++++");
			System.out.println("");
			}
	}
	private void CBR() {
		
		
		if (ACC == 0) {
			CSIAR = CSIAR + 2;
			SDR = Integer.parseInt(MEMORY[PSIAR].split("\\s")[1]);
			SAR = PSIAR;
			PSIAR = SDR;
		} else 
		{
		CSIAR = CSIAR +1;
	
		TMPR = ACC;
		ACC = PSIAR + 1;
		PSIAR = ACC;
		ACC = TMPR;
		CSIAR = 0;
		}
		
		if (info == 1) {
			System.out.println(PSIAR + " "+ MEMORY[PSIAR-1]);
			System.out.println("Registers:");
			System.out.println("ACC= " + ACC);
			System.out.println("PSIAR= " + PSIAR);
			System.out.println("SAR= " + SAR);
			System.out.println("SDR= " + SDR);
			System.out.println("TMPR= " + TMPR);
			System.out.println("+++++++++++++++++++++");
			System.out.println("");
			}
		
	}
	
	private void LDI(){
		
		
		
	
		SDR = Integer.parseInt(MEMORY[PSIAR].split("\\s")[1]); 
		
	
		ACC = PSIAR+1; 
		SAR = PSIAR;
		PSIAR = ACC;
		ACC = SDR;
		CSIAR = 0;
		
		if (info == 1) {
		System.out.println(PSIAR + " "+ MEMORY[PSIAR-1]);
		System.out.println("Registers:");
		System.out.println("ACC= " + ACC);
		System.out.println("PSIAR= " + PSIAR);
		System.out.println("SAR= " + SAR);
		System.out.println("SDR= " + SDR);
		System.out.println("TMPR= " + TMPR);
		System.out.println("+++++++++++++++++++++");
		System.out.println("");
		}
	}
	
	
	private void EXIT_SHARKOS() {
		
	}
	
	
		

}
