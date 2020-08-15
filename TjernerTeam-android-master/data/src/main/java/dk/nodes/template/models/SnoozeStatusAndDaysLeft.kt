package dk.nodes.template.models

class SnoozeStatusAndDaysLeft(
        var snoozeDate : String?,
        var userIsSnoozed : Boolean?


) {
    override fun toString(): String {
        return "SnoozeStatusAndDaysLeft(snoozeDate=$snoozeDate, userIsSnoozed=$userIsSnoozed)"
    }
}
