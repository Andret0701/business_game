package business_game.game_engine.types;

public class Transform {
    private Vector2 position;
    private double scale;
    private double angle;
    private Transform parent;

    public Transform() {
        this(0, 0, 1, 0);
    }

    public Transform(double x, double y, double scale, double angle) {
        setPosition(new Vector2(x, y));
        setScale(scale);
        setAngle(angle);
    }

    public Vector2 getPosition() {
        return localToWorldPosition(new Vector2(0, 0));
    }

    public Vector2 getLocalPositon() {
        return position;
    }

    public void setPosition(Vector2 position) {
        if (parent != null)
            position = parent.worldToLocalPosition(position);
        this.position = position;
        updateMatrices();
    }

    public void setLocalPosition(Vector2 position) {
        this.position = position;
        updateMatrices();
    }

    public void translate(Vector2 offset) {
        Vector2 position = getPosition();
        position.add(offset);
        setPosition(position);
    }

    public double getScale() {
        return localToWorldScale(1);
    }

    public double getLocalScale() {
        return scale;
    }

    public void setScale(double scale) {
        if (parent != null)
            scale = parent.worldToLocalScale(scale);
        this.scale = scale;
        updateMatrices();
    }

    public void setLocalScale(double scale) {
        this.scale = scale;
        updateMatrices();
    }

    public void scale(double scale) {
        setScale(getScale() * scale);
    }

    public double getAngle() {
        return localToWorldAngle(0);
    }

    public double getLocalAngle() {
        return angle;
    }

    public void rotate(double angle) {
        setAngle(getAngle() + angle);
    }

    public void setAngle(double angle) {
        if (parent != null)
            angle = parent.worldToLocalAngle(angle);
        this.angle = angle;
        updateMatrices();
    }

    public void setLocalAngle(double angle) {
        this.angle = angle;
        updateMatrices();
    }

    public Transform getParent() {
        return parent;
    }

    public void setParent(Transform parent) {
        Vector2 position = getPosition();
        double scale = getScale();
        double angle = getAngle();
        this.parent = parent;
        setPosition(position);
        setScale(scale);
        setAngle(angle);
    }

    private double[] matrix33vector3(double[][] matrix, double[] vector) {
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = 0;
            for (int j = 0; j < 3; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    private double transform_matrix[][];
    private double inverse_transform_matrix[][];

    private void updateMatrices() {
        transform_matrix = getTransformMatrix();
        inverse_transform_matrix = GetInverseTransformMatrix();
    }

    private double[][] getTransformMatrix() {
        double sina = Math.sin(angle);
        double cosa = Math.cos(angle);
        double[][] matrix = new double[][] {
                { cosa * scale, -sina * scale, position.x },
                { sina * scale, cosa * scale, position.y },
                { 0, 0, 1 }
        };
        return matrix;
    }

    private double[][] GetInverseTransformMatrix() {
        double sina = Math.sin(angle);
        double cosa = Math.cos(angle);
        double[][] matrix = new double[][] {
                { cosa / scale, sina / scale, -position.x * cosa / scale - position.y * sina / scale },
                { -sina / scale, cosa / scale, position.x * sina / scale - position.y * cosa / scale },
                { 0, 0, 1 }
        };
        return matrix;
    }

    public Vector2 transform(Vector2 point) {
        double[] vector = new double[] { point.x, point.y, 1 };
        double[] result = matrix33vector3(transform_matrix, vector);
        return new Vector2(result[0], result[1]);
    }

    public Vector2 inverseTransform(Vector2 point) {
        double[] vector = new double[] { point.x, point.y, 1 };
        double[] result = matrix33vector3(inverse_transform_matrix, vector);
        return new Vector2(result[0], result[1]);
    }

    public Vector2 localToWorldPosition(Vector2 local) {
        Vector2 out = transform(local.copy());
        if (parent != null)
            out = parent.localToWorldPosition(out);
        return out;
    }

    public Vector2 worldToLocalPosition(Vector2 world) {
        Vector2 out = world.copy();
        if (parent != null)
            out = parent.worldToLocalPosition(out);
        return inverseTransform(out);
    }

    public double localToWorldScale(double local) {
        double out = local * scale;
        if (parent == null)
            return out;
        return parent.localToWorldScale(out);
    }

    public double worldToLocalScale(double world) {
        double out = world / scale;
        if (parent == null)
            return out;
        return parent.worldToLocalScale(out);
    }

    public double localToWorldAngle(double local) {
        double out = local + angle;
        if (parent == null)
            return out;
        return parent.localToWorldAngle(out);
    }

    public double worldToLocalAngle(double world) {
        double out = world - angle;
        if (parent == null)
            return out;
        return parent.worldToLocalAngle(out);
    }

    public void setTransform(Transform transform) {
        position = transform.position;
        scale = transform.scale;
        angle = transform.angle;
        parent = transform.parent;
    }

    public Transform copy() {
        Transform transform = new Transform(position.x, position.y, scale, angle);
        transform.parent = parent;
        return transform;
    }

    @Override
    public String toString() {
        return "position = " + position + ", scale = " + scale + ", angle = " + angle;
    }

}
