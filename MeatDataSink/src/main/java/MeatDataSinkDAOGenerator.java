/**
 * Created by greg on 6/13/14.
 */

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MeatDataSinkDAOGenerator {

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "com.kramerica.meatworldavatar");
        schema.setDefaultJavaPackageDao("com.kramerica.meatworldavatar.meatdomain");
        schema.setDefaultJavaPackageTest("com.kramerica.meatworldavatar.test");

        Entity habit = schema.addEntity("Habit");
        habit.addIdProperty();
        Property avatarId = habit.addLongProperty("avatarId").notNull().getProperty();
        habit.addIntProperty("hour");
        habit.addIntProperty("weekday");
        habit.addIntProperty("month");
        habit.addIntProperty("dayOfMonth");
        habit.addIntProperty("dayOfYear");

        Entity acceleration = schema.addEntity("Acceleration");
        acceleration.addIdProperty();
        acceleration.addDateProperty("timestamp").indexDesc("accelTime", true);
        acceleration.addFloatProperty("x");
        acceleration.addFloatProperty("y");
        acceleration.addFloatProperty("z");
        Property associatedEvent = acceleration.addLongProperty("eventId").getProperty();

        Entity event = schema.addEntity("Event");
        event.addIdProperty().getProperty();
        Property eventAvatar = event.addLongProperty("avatarId").getProperty();
        event.addDateProperty("time");
        event.addIntProperty("sensor_type");
        ToMany eventAssociation = event.addToMany(acceleration, associatedEvent);

        Entity relevance = schema.addEntity("Relevance");
        relevance.addIdProperty();
        Property eventId = relevance.addLongProperty("eventId").getProperty();
        Property habitId = relevance.addLongProperty("habitId").getProperty();
        Property relevantAvatarId = relevance.addLongProperty("avatarId").getProperty();
        relevance.addFloatProperty("habitRelevance");
        relevance.addFloatProperty("eventRelevance");
        ToMany relevanceToEvent = relevance.addToMany(event, eventId);
        ToMany relevanceToHabit = relevance.addToMany(habit, habitId);

        Entity avatar = schema.addEntity("Avatar");
        avatar.addIdProperty();
        avatar.addDateProperty("birthday");
        avatar.addFloatProperty("height");
        avatar.addFloatProperty("weight");
        avatar.addByteProperty("gender");
        ToMany avatarToHabit = avatar.addToMany(habit, avatarId);
        ToMany avatarRelevance = avatar.addToMany(relevance, relevantAvatarId);
        ToMany avatarEvents = avatar.addToMany(event, eventAvatar);

        new DaoGenerator().generateAll(schema, ".");

    }
}
