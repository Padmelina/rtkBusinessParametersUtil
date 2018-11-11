package bp.model;

public enum ApplicationStep {
    FileParsingStep("FileParsingStep"),
    FileCorrectStep("FileCorrectStep"),
    FileCheckingStep("FileCheckingStep"),
    ScriptStep("ScriptStep"),
    CheckEnded("CheckEnded"),
    StatisticSuccessStep("StatisticSuccessStep"),
    StatisticErrorsStep("StatisticErrorsStep");

    private String stepName;

    ApplicationStep(String stepName) {
        this.stepName = stepName;
    }

    public String getStepName() {
        return stepName;
    }

    public static ApplicationStep parseStep(String name) {
        if (FileParsingStep.stepName.equals(name)) return FileParsingStep;
        if (FileCorrectStep.stepName.equals(name)) return FileCorrectStep;
        if (FileCheckingStep.stepName.equals(name)) return FileCheckingStep;
        if (ScriptStep.stepName.equals(name)) return ScriptStep;
        if (StatisticSuccessStep.stepName.equals(name)) return StatisticSuccessStep;
        if (ScriptStep.stepName.equals(name)) return ScriptStep;
        if (StatisticErrorsStep.stepName.equals(name)) return StatisticErrorsStep;
        return null;
    }
}
