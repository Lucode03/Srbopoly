using Backend.Domain;

namespace Backend.Factories
{
    public class FieldFactory
    {
        public static Field CreateField(int FieldID)
        {
            //
            return new PropertyField();
        }
    }
}