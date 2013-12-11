package generic.process.context.itf;

public interface OutboundProcessInstanceCtx {
	public void doAction(String actionName);
	public boolean eval(String conditionName);
}
