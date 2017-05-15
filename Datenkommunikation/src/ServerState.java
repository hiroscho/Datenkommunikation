
public class ServerState {
	enum State {
		Init, SNext, SLeft, SRight;
	}

	private State state;

	public ServerState() {
		state = State.Init;
	}

	public State getState() {
		return state;
	}

	public String processCommand(String cmd) {
		switch (state) {
		case Init:
			if (cmd.equals("Go")) {
				state = State.SNext;
				return "Gone";
			}
			break;
		case SNext:
			if (cmd.equals("Left")) {
				state = State.SLeft;
				return "WentLeft";
			}
			if(cmd.equals("Right")) {
				state = State.SRight;
				return "WentRight";
			}
			break;
		case SLeft:
			if (cmd.equals("Back")) {
				state = State.Init;
				return "WentBack";
			}
			break;
		case SRight:
			if (cmd.equals("OnceMore")) {
				state = State.SNext;
				return "DidOnceMore";
			}
			break;
		}
		return "did nothing";
	}

}
