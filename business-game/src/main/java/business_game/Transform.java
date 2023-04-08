package business_game;

public class Transform {
    public Vector2 position;
    public double scale;
    public double angle;
    public Transform parent;

    public Transform()

    {
        this(0, 0, 1, 0);
    }

    public Transform(double x, double y, double scale, double angle) {
        this.position = new Vector2(x, y);
        this.scale = scale;
        this.angle = angle;
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

    private double[][] get_transform_matrix(double x, double y, double scale, double angle) {
        double cosa = Math.cos(angle);
        double sina = Math.sin(angle);
        double[][] matrix = new double[][] {
                { cosa * scale, -sina * scale, x },
                { sina * scale, cosa * scale, y },
                { 0, 0, 1 }
        };
        return matrix;
    }

    private double[][] get_inverse_transform_matrix(double x, double y, double scale, double angle) {
        double cosa = Math.cos(angle);
        double sina = Math.sin(angle);
        double[][] matrix = new double[][] {
                { cosa / scale, sina / scale, -x * cosa / scale - y * sina / scale },
                { -sina / scale, cosa / scale, x * sina / scale - y * cosa / scale },
                { 0, 0, 1 }
        };
        return matrix;
    }

    public Vector2 transform(Vector2 point) {
        double[] vector = new double[] { point.x, point.y, 1 };
        double[][] matrix = get_transform_matrix(position.x, position.y, scale, angle);
        double[] result = matrix33vector3(matrix, vector);
        return new Vector2(result[0], result[1]);
    }

    public Vector2 inverseTransform(Vector2 point) {
        double[] vector = new double[] { point.x, point.y, 1 };
        double[][] matrix = get_inverse_transform_matrix(position.x, position.y, scale, angle);
        double[] result = matrix33vector3(matrix, vector);
        return new Vector2(result[0], result[1]);
    }

    public Vector2 localToWorldPosition(Vector2 local) {
        Vector2 out = transform(local.copy());
        if (parent == null)
            return out;
        return parent.localToWorldPosition(out);
    }

    public Vector2 worldToLocalPosition(Vector2 world) {
        Vector2 out = inverseTransform(world.copy());
        if (parent == null)
            return out;
        return parent.worldToLocalPosition(out);
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
}
