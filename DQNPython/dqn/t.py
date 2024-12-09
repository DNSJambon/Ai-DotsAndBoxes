import numpy as np
import onnxruntime as ort

ort_session = ort.InferenceSession("policy_net.onnx")
input_name = ort_session.get_inputs()[0].name
output_name = ort_session.get_outputs()[0].name

dummy_input = np.array([1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0], dtype=np.float32).reshape(1, 12)
outputs = ort_session.run([output_name], {input_name: dummy_input})
print("Inference success:", outputs)
